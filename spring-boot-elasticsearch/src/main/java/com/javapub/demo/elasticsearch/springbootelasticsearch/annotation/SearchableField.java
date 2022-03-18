package com.javapub.demo.elasticsearch.springbootelasticsearch.annotation;

import com.javapub.demo.elasticsearch.springbootelasticsearch.enums.ESAnalyzer;
import com.javapub.demo.elasticsearch.springbootelasticsearch.enums.ESType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchableField {

    /**
     * 映射类型
     */
    ESType type() default ESType.KEYWORD;


    /**
     * es分词类型
     */
    ESAnalyzer analyzer() default ESAnalyzer.DEFAULT;

    /**
     * copy to
     */
    String copyTo() default "";

    /**
     * 指定是否需要被分词，如果不需要则精确匹配
     */
    boolean isAnalyze() default false;
}
