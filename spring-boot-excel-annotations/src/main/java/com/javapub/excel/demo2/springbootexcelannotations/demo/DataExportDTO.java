package com.javapub.excel.demo2.springbootexcelannotations.demo;


public class DataExportDTO {

    @ExportField(columnIndex = 0, title = "编号")
    private String id;

    @ExportField(columnIndex = 1, title = "名字")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
