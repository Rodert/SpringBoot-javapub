package com.javapub.excel.demo.springbootexcel.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * workbook > sheet > row > cell
 */
public class DisExcel {

    /**
     * @param fileName
     * @return
     */
    List<Sheet> getSheets(String fileName) throws Exception {
        List<Sheet> sheets = new ArrayList<>();
        Workbook workbook = getWorkbook(fileName);
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);
            sheets.add(sheet);
        }
//        workbook.forEach();
        return sheets;
    }

    /**
     * 获取workbook
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    Workbook getWorkbook(String fileName) throws Exception {
        Workbook workbook = null;
        FileInputStream fileInputStream = null;
        // 获取Excel后缀名
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        // 获取Excel文件
        File excelFile = new File(fileName);
        // 获取Excel工作簿
        fileInputStream = new FileInputStream(excelFile);

        if (fileType.contains("xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } else if (fileType.contains("xls")) {
            workbook = new HSSFWorkbook(fileInputStream);
        } else {
            throw new Exception("cannot read unknown formats ! ");
        }
        return workbook;
    }

}
