package com.javapub.demo.elasticsearch.springbootelasticsearch.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javapub.demo.elasticsearch.springbootelasticsearch.service.DocService;
import com.javapub.demo.elasticsearch.springbootelasticsearch.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api("测试数据接口")
@RestController
@RequestMapping("internal")
public class InitController {

    private static final Logger logger = LoggerFactory.getLogger(InitController.class);

    @Value("${custom.index}")
    private String indexName;

    @Autowired
    private IndexService indexService;
    @Autowired
    private DocService docService;

    /**
     * 构建测试数据
     */
    @ApiOperation("新建测试索引及测试数据")
    @RequestMapping("init")
    @ResponseBody
    public String init(@RequestParam("type") String type) {
        if (!type.equals("news")) {
            return "非预设 news";
        }
//        String indexName = "news-index" + getNowDate();
        new Thread(() -> {
            try {
                Boolean aBoolean = indexService.initIndex(indexName);
                if (aBoolean) {
                    docService.batchInsert(indexName, getInitNewsData());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return "【" + indexName + "】 has start";
    }

    private String getNowDate() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd-HH-mm-ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    /**
     * 获取一些初始化数据
     *
     * @return
     */
    private JSONArray getInitNewsData() throws IOException {
        JSONArray jsonArray = new JSONArray();
        Resource resource = new ClassPathResource("static/data/news.txt");
        String path = resource.getFile().getPath();
        List<String> stringList = FileUtils.readLines(new File(path), StandardCharsets.UTF_8);
        stringList.forEach(s -> {
            JSONObject jsonObject = JSONObject.parseObject(s);
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

}
