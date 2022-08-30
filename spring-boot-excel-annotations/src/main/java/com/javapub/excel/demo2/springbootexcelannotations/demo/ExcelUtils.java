package com.javapub.excel.demo2.springbootexcelannotations.demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    public static final int MAX_EXPORT_LIMIT = 50000;
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {
    }

    public static void bean2Excel(XSSFWorkbook workbook, List beanList, Class clazz) throws Exception {
        XSSFSheet sheet = workbook.createSheet();

        int length = clazz.getDeclaredFields().length;
        putWaterRemarkToExcel(workbook, sheet, String.join(SignConstant.UNDER_LINE, "水印", "JavaPub"), length);
        createRowData(clazz, beanList, sheet);
    }


    public static void putWaterRemarkToExcel(Workbook wb, Sheet sheet, String content, int fieldLength) {

        // 加载图片
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            Integer width = 300;
            Integer height = 100;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
//            InitProperties initProperties = SpringContextUtil.getBean(InitProperties.class);
//            String fontType = initProperties.getFont();
            String fontType = "\\uFFFD\\uFFFD\\uFFFD\\uFFFD"; // 字体
            Integer fontSize = 30;
            Font font = new Font(fontType, Font.PLAIN, fontSize);
            Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
            image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = image.createGraphics();
            g2d.setColor(new java.awt.Color(0, 0, 0, 80)); //设置字体颜色和透明度
            g2d.setStroke(new BasicStroke(1)); // 设置字体
            g2d.rotate(Math.toRadians(-10), (double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置倾斜度
            FontRenderContext context = g2d.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(content, context);
            double x = (width - bounds.getWidth()) / 2 + 50;
            double y = (height - bounds.getHeight()) / 2;
            double ascent = -bounds.getY();
            double baseY = y + ascent;
            // 写入水印文字原定高度过小，所以累计写水印，增加高度
            g2d.drawString(content, (int) x, (int) baseY);
            // 设置透明度
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 释放对象
            g2d.dispose();
            ImageIO.write(image, "png", byteArrayOut);

            // 开始打水印
            Drawing drawing = sheet.createDrawingPatriarch();

            int startXCol = fieldLength / 4;
            int startYRow = 1;
            int betweenXCol = 1;
            int betweenYRow = 1;
            int XCount = 1;
            int YCount = 1;

            // 按照共需打印多少行水印进行循环
            for (int yCount = 0; yCount < YCount; yCount++) {
                // 按照每行需要打印多少个水印进行循环
                for (int xCount = 0; xCount < XCount; xCount++) {
                    // 创建水印图片位置
                    int xIndexInteger = startXCol + (xCount * width / 50) + (xCount * betweenXCol);
                    int yIndexInteger = startYRow + (yCount * height / 50) + (yCount * betweenYRow);
                    /*
                     * 参数定义： 第一个参数是（x轴的开始节点）； 第二个参数是（是y轴的开始节点）； 第三个参数是（是x轴的结束节点）；
                     * 第四个参数是（是y轴的结束节点）； 第五个参数是（是从Excel的第几列开始插入图片，从0开始计数）；
                     * 第六个参数是（是从excel的第几行开始插入图片，从0开始计数）； 第七个参数是（图片宽度，共多少列）；
                     * 第8个参数是（图片高度，共多少行）；
                     */
                    ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger, yIndexInteger, xIndexInteger + width, yIndexInteger + height);

                    Picture pic = drawing.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                    pic.resize();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    private static void createRowData(Class clazz, List beanList, XSSFSheet sheet) throws Exception {
        if (CollectionUtils.isNotEmpty(beanList) && beanList.size() > MAX_EXPORT_LIMIT) {
            throw new Exception();
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Map<String, Method> fieldNameWriteMethodMap = Maps.newHashMap();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getPropertyType() == Class.class) {
                    continue;
                }
                Method readMethod = propertyDescriptor.getReadMethod();
                fieldNameWriteMethodMap.put(propertyDescriptor.getName(), readMethod);
            }
            Field[] declaredFields = clazz.getDeclaredFields();
            XSSFRow titleRow = sheet.createRow(0);
            for (Field declaredField : declaredFields) {
                ExportField annotation = declaredField.getAnnotation(ExportField.class);
                if (annotation == null) {
                    continue;
                }
                String title = annotation.title();
                XSSFCell cell = titleRow.createCell(annotation.columnIndex());
                cell.setCellValue(title);
            }
            int rowIndex = 1;
            for (Object object : beanList) {
                XSSFRow row = sheet.createRow(rowIndex);
                for (Field declaredField : declaredFields) {
                    ExportField annotation = declaredField.getAnnotation(ExportField.class);
                    if (annotation == null) {
                        continue;
                    }
                    int columnIndex = annotation.columnIndex();
                    XSSFCell cell = row.createCell(columnIndex);
                    Method method = fieldNameWriteMethodMap.get(declaredField.getName());
                    Object value = method.invoke(object);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
                rowIndex++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static List excel2Bean(String path, int sheetIndex, Class clazz) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            int lastRowNum = sheet.getLastRowNum();

//            UploadFileProperties uploadFileProperties = SpringContextUtil.getBean(UploadFileProperties.class);

            Field[] declaredFields = clazz.getDeclaredFields();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Map<String, Method> fieldNameWriteMethodMap = Maps.newHashMap();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getPropertyType() == Class.class) {
                    continue;
                }
                Method writeMethod = propertyDescriptor.getWriteMethod();
                fieldNameWriteMethodMap.put(propertyDescriptor.getName(), writeMethod);
            }
            List retList = Lists.newArrayList();
            int count = 0;
            for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                int rowLimit = 10000; //行数
                if (count > rowLimit + 1) {
                    throw new Exception();

                }
                XSSFRow row = sheet.getRow(rowIndex);
                if (row == null || row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                    continue;
                }
                Object object = clazz.newInstance();
                for (Field declaredField : declaredFields) {
                    ImportField annotation = declaredField.getAnnotation(ImportField.class);
                    if (annotation == null) {
                        continue;
                    }
                    int columnIndex = annotation.columnIndex();
                    XSSFCell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        Method method = fieldNameWriteMethodMap.get(declaredField.getName());
                        CellType cellTypeEnum = cell.getCellTypeEnum();
                        switch (cellTypeEnum) {
                            case STRING: {
                                method.invoke(object, cell.getStringCellValue());
                                break;
                            }
                            case NUMERIC: {
                                String rawValue = cell.getRawValue();
                                method.invoke(object, rawValue);
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }
                retList.add(object);
                count++;
            }
            return retList;
        } catch (IOException | InvocationTargetException | InstantiationException | IntrospectionException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    //    /**
    //     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
    //     */
    //    public List<List<Object>> getBankListByExcel(InputStream in, String fileName, int sheetnum, int colnum) throws Exception {
    //        List<List<Object>> list = null;
    //
    //        //创建Excel工作薄
    //        Workbook work = this.getWorkbook(in, fileName);
    //        if (null == work) {
    //            throw new IllegalArgumentException("创建Excel工作薄为空！");
    //        }
    //        Sheet sheet = null;
    //        Row row = null;
    //        Cell cell = null;
    //
    //        list = new ArrayList<>();
    //        //遍历Excel中所有的sheet
    //        for (int i = 0; i < sheetnum; i++) {
    //            sheet = work.getSheetAt(i);
    //            if (sheet == null) {
    //                continue;
    //            }
    //
    //            //遍历当前sheet中的所有行
    //            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
    //                row = sheet.getRow(j);
    //                if (row == null || j == 0) {
    //                    continue;
    //                }
    //                SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
    //                //遍历所有的列
    //                List<Object> li = new ArrayList<Object>();
    //                boolean flag = false;
    //                for (int y = 0; y < colnum; y++) {
    //                    cell = row.getCell(y);
    //                    if (cell == null || cell.toString() == null) {
    //                        li.add("");
    //                        continue;
    //                    }
    //                    CellType cellType = cell.getCellTypeEnum();
    //                    String cellValue = "";
    //                    switch (cellType) {
    //                        case STRING:     // 文本
    //                            cellValue = cell.getRichStringCellValue().getString();
    //                            break;
    //                        case NUMERIC:    // 数字、日期
    //                            if (DateUtil.isCellDateFormatted(cell)) {
    //                                cellValue = fmt.format(cell.getDateCellValue());
    //                            } else {
    //                                cell.setCellType(CellType.STRING);
    //                                cellValue = String.valueOf(cell.getRichStringCellValue().getString());
    //                            }
    //                            break;
    //                        case BOOLEAN:    // 布尔型
    //                            cellValue = String.valueOf(cell.getBooleanCellValue());
    //                            break;
    //                        case BLANK: // 空白
    //                            cellValue = cell.getStringCellValue();
    //                            break;
    //                        case ERROR: // 错误
    //                            break;
    //                        case FORMULA:    // 公式
    //                            cell.setCellType(CellType.STRING);
    //                            cellValue = String.valueOf(cell.getRichStringCellValue().getString());
    //                            break;
    //                        default:
    //                            cellValue = "";
    //                    }
    //                    if (StringUtils.isNotBlank(cellValue)) {
    //                        flag = true;
    //                    }
    //                    li.add(cellValue);
    //                }
    //                if (flag) {
    //                    list.add(li);
    //                }
    //
    //            }
    //        }
    //        return list;
    //    }
    //
    //    /**
    //     * 描述：根据文件后缀，自适应上传文件的版本
    //     */
    //    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
    //        Workbook wb = null;
    //        String fileType = fileName.substring(fileName.lastIndexOf("."));
    //        if (EXCEL_2003_L.equals(fileType)) {
    //            wb = new HSSFWorkbook(inStr);  //2003-
    //        } else if (EXCEL_2007_U.equals(fileType)) {
    //            wb = new XSSFWorkbook(inStr);  //2007+
    //        } else {
    //            throw new Exception("解析的文件格式有误！");
    //        }
    //        return wb;
    //    }

}