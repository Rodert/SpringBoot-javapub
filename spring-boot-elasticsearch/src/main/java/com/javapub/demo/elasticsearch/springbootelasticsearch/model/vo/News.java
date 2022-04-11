package com.javapub.demo.elasticsearch.springbootelasticsearch.model.vo;

import com.javapub.demo.elasticsearch.springbootelasticsearch.annotation.SearchId;
import com.javapub.demo.elasticsearch.springbootelasticsearch.annotation.SearchableField;
import com.javapub.demo.elasticsearch.springbootelasticsearch.enums.ESType;

import java.io.Serializable;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/4/5 20:56
 * @Version: 1.0
 * @Description:
 */
public class News implements Serializable {

    @SearchId
    @SearchableField
    private Long id;
    @SearchableField(type = ESType.KEYWORD)
    private String title;
    @SearchableField(type = ESType.KEYWORD)
    private String summary;
    @SearchableField(type = ESType.TEXT)
    private String content;
    @SearchableField(type = ESType.KEYWORD)
    private String url;
    /**
     * 是否认证新闻
     */
    @SearchableField(type = ESType.BOOLEAN)
    private Boolean isAuthority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAuthority() {
        return isAuthority;
    }

    public void setAuthority(Boolean authority) {
        this.isAuthority = authority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
