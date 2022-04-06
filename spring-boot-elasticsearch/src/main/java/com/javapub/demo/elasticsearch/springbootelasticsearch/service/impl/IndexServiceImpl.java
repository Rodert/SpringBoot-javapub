package com.javapub.demo.elasticsearch.springbootelasticsearch.service.impl;

import com.javapub.demo.elasticsearch.springbootelasticsearch.annotation.SearchableField;
import com.javapub.demo.elasticsearch.springbootelasticsearch.enums.ESAnalyzer;
import com.javapub.demo.elasticsearch.springbootelasticsearch.enums.ESType;
import com.javapub.demo.elasticsearch.springbootelasticsearch.model.vo.News;
import com.javapub.demo.elasticsearch.springbootelasticsearch.service.IndexService;
import com.javapub.demo.elasticsearch.springbootelasticsearch.utils.DateUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Service
public class IndexServiceImpl implements IndexService {

    public static final String PROPERTIES_STRING = "properties";
    public static final String TYPE_STRING = "type";
    public static final String NESTED_STRING = "nested";
    public static final String ANALYZER_STRING = "analyzer";
    public static final String SEARCH_ANALYZER_STRING = "search_analyzer";
    public static final String TEXT_STRING = "text";
    public static final String RAW_STRING = "raw";
    public static final String RAW_LOWER_STRING = "raw_lower";
    public static final String PIN_YIN_STRING = "pinyin";
    public static final String PIN_YIN_RAW_STRING = "pinyin_raw";
    public static final String FIELDS_STRING = "fields";
    public static final String FIELDDATA_STRING = "fielddata";
    public static final String TERM_VECTOR_STRING = "term_vector";
    public static final String WITH_POSITIONS_OFFSETS_STRING = "with_positions_offsets";
    public static final String FORMAT_STRING = "format";
    public static final String DATE_FORMAT_SUFFIX = " || epoch_millis";
    public static final String DOT_STRING = ".";
    public static final String ORG_ID_STRING = "tenantId";
    public static final String HIGHLIGHT_PRE_TAG = "\u200b";
    public static final String HIGHLIGHT_POST_TAG = "\u200c";
    public static final String COPY_TO_STRING = "copy_to";

    private static final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void initIndex(String indexName) throws IOException {
        Type type = News.class.getGenericSuperclass();
        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
        Class<News> newsClazz = (Class<News>) arguments[0];
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        createIndex(indexName);
        buildMapping(indexName, newsClazz, xContentBuilder);
        putMapping(indexName, xContentBuilder);
    }

    @Override
    public void createIndex(String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        if (logger.isDebugEnabled()) {
            logger.debug("result for create index-{} is {}", indexName, createIndexResponse.isAcknowledged());
        }
    }

    @Override
    public void buildMapping(String indexName, Class clazz, XContentBuilder xContentBuilder) throws IOException {
        xContentBuilder.startObject();
        xContentBuilder.startObject("properties");

        Field[] fields = getAllClassFields(clazz, null);
        buildFields(fields, xContentBuilder);

        xContentBuilder.endObject();
        xContentBuilder.endObject();
    }

    @Override
    public void putMapping(String indexName, XContentBuilder xContentBuilder) throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest(indexName).source(xContentBuilder);
        client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
    }

    private static void buildFields(Field[] fields, XContentBuilder xContentBuilder) throws IOException {
        for (Field field : fields) {
            if (field == null) {
                continue;
            }
            String fieldName = field.getName();
            SearchableField annotation = field.getAnnotation(SearchableField.class);
            if (annotation == null) {
                continue;
            }
            ESType esType = annotation.type();
            ESAnalyzer esAnalyzer = annotation.analyzer();
            switch (esType) {
                case OBJECT: {
                    buildObjectTypeContent(xContentBuilder, field, fieldName);
                    break;
                }
                case LIST: {
                    buildListTypeContent(xContentBuilder, field, fieldName);
                    break;
                }
                case TEXT: {
                    if (esAnalyzer == ESAnalyzer.PINYIN) {
                        buildPinYinField(xContentBuilder, fieldName);
                    } else {
                        if (esAnalyzer == ESAnalyzer.DEFAULT) {
                            xContentBuilder.startObject(fieldName);
                            xContentBuilder.field("type", esType.name().toLowerCase());
                            String copyToField = annotation.copyTo();
                            if (StringUtils.isNotBlank(copyToField)) {
                                xContentBuilder.field("copy_to", copyToField);
                            }
                            xContentBuilder.startObject("fields");
                            xContentBuilder.startObject("raw");
                            xContentBuilder.field("type", ESType.KEYWORD.name().toLowerCase());
                            xContentBuilder.endObject();
                            xContentBuilder.endObject();
                            xContentBuilder.endObject();
                        } else {
                            buildTextFieldsByAnalyzer(xContentBuilder, fieldName, esAnalyzer);
                        }
                    }
                    break;
                }
                case DATE: {
                    xContentBuilder.startObject(fieldName);
                    xContentBuilder.field(TYPE_STRING, esType.name().toLowerCase());
                    xContentBuilder.field(FORMAT_STRING, DateUtil.YMD_HMS_STR + "||" + DateUtil.YMD_STR + DATE_FORMAT_SUFFIX);
                    xContentBuilder.endObject();
                    break;
                }
                case JSON:
                    xContentBuilder.startObject(fieldName);
                    xContentBuilder.field(TYPE_STRING, ESType.OBJECT.name().toLowerCase());
                    xContentBuilder.endObject();
                    break;
                default: {
                    xContentBuilder.startObject(fieldName);
                    xContentBuilder.field(TYPE_STRING, esType.name().toLowerCase());
                    if (esAnalyzer != ESAnalyzer.DEFAULT) {
                        xContentBuilder.field(ANALYZER_STRING, esAnalyzer.name().toLowerCase());
                    }
                    xContentBuilder.endObject();
                }
            }

        }
    }

    private Field[] getAllClassFields(Class clazz, Field[] fields) {
        if (fields == null) {
            fields = clazz.getDeclaredFields();
        } else {
            fields = (Field[]) ArrayUtils.addAll(fields, clazz.getDeclaredFields());
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != Object.class) {
            fields = getAllClassFields(superclass, fields);
        }
        return fields;
    }

    private static void buildObjectTypeContent(XContentBuilder xContentBuilder, Field field, String fieldName) throws IOException {
        Class<?> type = field.getType();
        Field[] declaredFields = type.getDeclaredFields();
        xContentBuilder.startObject(fieldName);
        xContentBuilder.startObject("properties");
        buildFields(declaredFields, xContentBuilder);
        xContentBuilder.endObject();
        xContentBuilder.endObject();
    }

    private static void buildListTypeContent(XContentBuilder xContentBuilder, Field field, String fieldName) throws IOException {
        Class<?> tempType = field.getType();
        if (tempType == List.class || tempType == Set.class) {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Type[] actualTypeArguments = genericType.getActualTypeArguments();
            Class actualTypeArgument = (Class) actualTypeArguments[0];
            if (actualTypeArgument == String.class) {
                xContentBuilder.startObject(fieldName);
                xContentBuilder.field("type", ESType.KEYWORD.name().toLowerCase());
                xContentBuilder.endObject();
            } else {
                Field[] declaredFields = buildSuperClassFields(actualTypeArgument);
                xContentBuilder.startObject(fieldName);
                xContentBuilder.startObject("properties");
                buildFields(declaredFields, xContentBuilder);
                xContentBuilder.endObject();
                xContentBuilder.endObject();
            }
        }
    }

    private static Field[] buildSuperClassFields(Class actualTypeArgument) {
        Field[] fields = null;
        Field[] declaredFields = actualTypeArgument.getDeclaredFields();
        Type genericSuperclass = actualTypeArgument.getGenericSuperclass();
        if (genericSuperclass != Object.class) {
            Field[] superClassField = ((Class) genericSuperclass).getDeclaredFields();
            fields = (Field[]) ArrayUtils.addAll(declaredFields, superClassField);
            buildSuperClassFields((Class) genericSuperclass);
        } else {
            fields = declaredFields;
        }
        return fields;
    }

    private static void buildPinYinField(XContentBuilder xContentBuilder, String fieldName) throws IOException {
        xContentBuilder.startObject(fieldName);
        String name = ESType.TEXT.name();
        xContentBuilder.field(TYPE_STRING, name.toLowerCase());
        xContentBuilder.startObject(FIELDS_STRING);
        xContentBuilder.startObject(TEXT_STRING);
        xContentBuilder.field(TYPE_STRING, name.toLowerCase());
        xContentBuilder.field(ANALYZER_STRING, ESAnalyzer.STANDARD.name().toLowerCase());
        xContentBuilder.endObject();
        xContentBuilder.startObject(PIN_YIN_STRING);
        xContentBuilder.field(TYPE_STRING, name.toLowerCase());
        xContentBuilder.field(ANALYZER_STRING, ESAnalyzer.PINYIN.name().toLowerCase());
        xContentBuilder.endObject();
        xContentBuilder.startObject(RAW_STRING);
        xContentBuilder.field(TYPE_STRING, ESType.KEYWORD.name().toLowerCase());
        xContentBuilder.endObject();
        xContentBuilder.startObject(PIN_YIN_RAW_STRING);
        xContentBuilder.field(TYPE_STRING, ESType.TEXT.name().toLowerCase());
        xContentBuilder.field(ANALYZER_STRING, ESAnalyzer.PINYIN_RAW.name().toLowerCase());
        xContentBuilder.field(FIELDDATA_STRING, true);
        xContentBuilder.endObject();
        xContentBuilder.startObject(RAW_LOWER_STRING);
        xContentBuilder.field(TYPE_STRING, name.toLowerCase());
        xContentBuilder.field(ANALYZER_STRING, ESAnalyzer.RAW_LOWER.name().toLowerCase());
        xContentBuilder.endObject();
        xContentBuilder.endObject();
        xContentBuilder.endObject();
    }

    private static void buildTextFieldsByAnalyzer(XContentBuilder xContentBuilder, String fieldName, ESAnalyzer analyzer) throws IOException {
        xContentBuilder.startObject(fieldName);
        String name = ESType.TEXT.name();
        xContentBuilder.field(TYPE_STRING, name.toLowerCase());
        xContentBuilder.startObject(FIELDS_STRING);
        xContentBuilder.startObject(TEXT_STRING);
        xContentBuilder.field(TYPE_STRING, name.toLowerCase());
        xContentBuilder.field(ANALYZER_STRING, analyzer.name().toLowerCase());
        xContentBuilder.endObject();
        xContentBuilder.startObject(RAW_STRING);
        xContentBuilder.field(TYPE_STRING, ESType.KEYWORD.name().toLowerCase());
        xContentBuilder.endObject();
        xContentBuilder.endObject();
        xContentBuilder.endObject();
    }
}
