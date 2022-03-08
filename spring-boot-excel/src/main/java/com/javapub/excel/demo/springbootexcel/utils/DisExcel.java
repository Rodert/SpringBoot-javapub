package com.javapub.excel.demo.springbootexcel.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * workbook > sheet页 > row行 > cell列
 */
public class DisExcel {
    public static void main(String[] args) throws Exception {
        String fileName = "E:\\javapubsworkpace\\SpringBoot-javapub\\spring-boot-excel\\src\\main\\resources\\test.xlsx";
        DisExcel disExcel = new DisExcel();
        Map<String, List<Row>> rowsMap = disExcel.getRows(fileName);
        for (String s : rowsMap.keySet()) {
            List<Row> rows = rowsMap.get(s);
            System.out.println("sheetName：" + s + " 包含行数：" + rows.size());
        }
    }

    Map<String, List<Row>> getRows(String fileName) throws Exception {
        List<Sheet> sheets = getSheets(fileName);
        Map<String, List<Row>> sheetNameAndRowsMap = new HashMap<>(sheets.size());
        for (Sheet sheet : sheets) {
            String sheetName = sheet.getSheetName();
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows(); // sheet总行数
            List<Row> rows = new ArrayList<>(physicalNumberOfRows);
            for (int i = 0; i < physicalNumberOfRows; i++) {
                rows.add(sheet.getRow(i));
            }
            sheetNameAndRowsMap.put(sheetName, rows);
        }
        return sheetNameAndRowsMap;
    }

    /**
     * 获取 sheet
     *
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
