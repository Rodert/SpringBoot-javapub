package com.javapub.demo.elasticsearch.springbootelasticsearch.scheduled;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javapub.demo.elasticsearch.springbootelasticsearch.model.vo.News;
import com.javapub.demo.elasticsearch.springbootelasticsearch.service.DocService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * 采集资讯数据用于测试
 */
@Component
public class NewsSpider {

    private static final Logger logger = LoggerFactory.getLogger(NewsSpider.class);

    @Value("${custom.index}")
    private String indexName;
    @Autowired
    private DocService docService;

    @Scheduled(cron = "* 0/5 * * * ?")
    void xinhuaTask() throws IOException {
        String domain = "http://www.xinhuanet.com/tech/dgkj/index.html";
        logger.info("{} start {}", this.getClass().getSimpleName(), System.currentTimeMillis());

        Document document = Jsoup.connect(domain).get();
        //列表解析
        Elements elementsByClass = document.getElementsByClass("item item-style1");
        List<String> urlList = new java.util.ArrayList<>(Collections.emptyList());
        for (Element element : elementsByClass) {
            String href = Objects.requireNonNull(element.getElementsByTag("a").first()).attr("abs:href").trim();
            if (!href.equals("")) urlList.add(href);
        }
        //正文解析
        JSONArray jsonArray = new JSONArray();
        for (String url : urlList) {
            News news = new News();
            news.setUrl(url);
            document = Jsoup.connect(url).get();
            news.setTitle(getTitle(document));
            news.setContent(Objects.requireNonNull(document.getElementById("detail")).text().replaceAll("\n", " ").replaceAll("\r", " ").trim());
            jsonArray.add(JSONObject.toJSON(news));
        }
        docService.batchInsert(indexName, jsonArray);
        logger.info("{} end {}", this.getClass().getSimpleName(), System.currentTimeMillis());
    }

    private static String getTitle(Document document) {
        String title = document.title().trim();
        if (title.equals("")) {
            title = Objects.requireNonNull(document.getElementsByTag("title").first()).text().trim();
        }
        return title;
    }

    public static void main(String[] args) throws IOException {
//        xinhuaTask();
    }
}
