package com.javapub.excel.demo2.springbootexcelannotations.demo;

import com.elensdata.black.enums.ResponseMsg;
import com.elensdata.black.exception.ResponseMsgException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leilei on 2016/12/24.
 */
public class FileUtil {
    public static final String NGINX_PREFIX = "file";
    public static final String NGINX_API_PREFIX = "api";
    public static final String NGINX_FILE_DOWNLOAD_PREFIX = "download";
    public static final List<String> SUPPORTED_FILE;
    public static final List<String> SUPPORTED_PIC;
    public static final String SUFFIX = ".blacklist";
    public static final String EXPORT_PREFIX = "data-";
    public static final String IMPORT_PREFIX = "tempate-";
    public static final String XLS_SUFFIX = ".xls";
    public static final int MAX_FILE_SIZE = 50 * 1024 * 1024;
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    static {
        SUPPORTED_FILE = new ArrayList<>();
        SUPPORTED_FILE.add(".txt");
        SUPPORTED_FILE.add(".ppt");
        SUPPORTED_FILE.add(".pptx");
        SUPPORTED_FILE.add(".xls");
        SUPPORTED_FILE.add(".xlsx");
        SUPPORTED_FILE.add(".rar");
        SUPPORTED_FILE.add(".pdf");
        SUPPORTED_FILE.add(".zip");
        SUPPORTED_FILE.add(".png");
        SUPPORTED_FILE.add(".jpg");
        SUPPORTED_FILE.add(".jpeg");
        SUPPORTED_FILE.add(".numbers");
        SUPPORTED_FILE.add(".doc");
        SUPPORTED_FILE.add(".docx");

        SUPPORTED_PIC = new ArrayList<>();
        SUPPORTED_PIC.add(".jpg");
        SUPPORTED_PIC.add(".jpeg");
        SUPPORTED_PIC.add(".png");
        SUPPORTED_PIC.add(".gif");
    }

    private FileUtil() {
    }

    public static String getMD5(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            String md5String = new BigInteger(1, md5.digest()).toString(16);
            return md5String;
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseMsgException(ResponseMsg.COMMON_ERROR_MSG);
        }
    }

    public static byte[] getBytes(InputStream inputStream) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            bos.close();
            inputStream.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return buffer;
    }

    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(1024)) {
                byte[] b = new byte[1024];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                buffer = bos.toByteArray();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return buffer;
    }

    public static String getSuffix(String originalFilename) {
        if (StringUtils.isBlank(originalFilename)) {
            return SUFFIX;
        }
        int offset = originalFilename.lastIndexOf(SignConstant.DOT_SEPARATOR);
        if (offset < 0) {
            return SUFFIX;
        }
        String suffix = originalFilename.substring(offset, originalFilename.length());
        if (SUPPORTED_FILE.contains(suffix.toLowerCase())) {
            return suffix;
        } else {
            return suffix + SUFFIX;
        }
    }

    public static String getOriginSuffix(String originalFilename) {
        int offset = originalFilename.lastIndexOf(SignConstant.DOT_SEPARATOR);
        if (offset < 0) {
            return null;
        }
        return originalFilename.substring(offset, originalFilename.length());
    }

    public static String removeSuffix(String originalFilename) {
        int offset = originalFilename.lastIndexOf(SignConstant.DOT_SEPARATOR);
        if (offset < 0) {
            return originalFilename;
        }
        return originalFilename.substring(0, offset);
    }

    public static String generateLocalPath(String uploadBasePath, String fileUrl, String domain) {
        String path = fileUrl.replace(String.join(File.separator, domain, NGINX_PREFIX), "");
        return uploadBasePath + path;
    }

    /**
     * 生成文件路径并生成相关文件夹
     */
    public static String generateFileSystemPath(String orgId, String fileUploadBasePath, String fileMd5, String fileSuffix) {
        fileUploadBasePath = fileUploadBasePath.endsWith(File.separator) ? fileUploadBasePath : (fileUploadBasePath + File.separator);
        fileUploadBasePath = fileUploadBasePath + orgId + File.separator;

        String date_ymd = LocalDate.now().toString(DateUtil.YMD_STR);

        File dirFile = new File(fileUploadBasePath + date_ymd);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String path = orgId + File.separator + date_ymd + File.separator + fileMd5 + fileSuffix;

        return path;
    }

    public static int getFileSize(String urlStr) {
        int length = 0;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            length = urlCon.getContentLength();
            urlCon.disconnect();
        } catch (IOException e) {
            logger.error("getFileSizeError", e);
            throw new ResponseMsgException(ResponseMsg.COMMON_ERROR_MSG);
        }
        return length;
    }

    /**
     * byte数组写入file
     *
     * @return 目标file是否存在
     */
    public static boolean byte2File(String uploadBasePath, byte[] buffer, String path) {
        File file = new File(uploadBasePath + path);
        if (file.exists()) {
            return true;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                bufferedOutputStream.write(buffer);
                bufferedOutputStream.flush();
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

}
