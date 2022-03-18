package com.javapub.demo.elasticsearch.springbootelasticsearch.model.vo;

import com.javapub.demo.elasticsearch.springbootelasticsearch.annotation.SearchId;
import com.javapub.demo.elasticsearch.springbootelasticsearch.annotation.SearchableField;
import com.javapub.demo.elasticsearch.springbootelasticsearch.enums.ESType;

import java.io.Serializable;

public class News implements Serializable {

    @SearchId
    @SearchableField
    private Long id;
    @SearchableField(type = ESType.KEYWORD)
    private String title;
    @SearchableField(type = ESType.KEYWORD)
    private String summary;

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

}
