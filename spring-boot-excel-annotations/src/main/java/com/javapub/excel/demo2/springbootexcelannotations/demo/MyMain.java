package com.javapub.excel.demo2.springbootexcelannotations.demo;

import com.google.common.collect.Lists;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MyMain {
    public static void main(String[] args) {
        HttpServletResponse response = getResponse();
        try {
            FileDownloadUtil.configDownloadHeader(response, "写出文件-重要资料");
            export((Lists.newArrayList("123")),
                    response.getOutputStream(), response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void export(ArrayList<String> newArrayList, ServletOutputStream outputStream, ServletOutputStream destOutputStream) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            List dataExportDTOs = new ArrayList();//待写入数据
            ExcelUtils.bean2Excel(workbook, dataExportDTOs, DataExportDTO.class);
            workbook.write(destOutputStream);
        } catch (Exception e) {
        }
    }

    private static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
