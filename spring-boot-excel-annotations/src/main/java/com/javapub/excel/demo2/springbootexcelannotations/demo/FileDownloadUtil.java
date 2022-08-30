package com.javapub.excel.demo2.springbootexcelannotations.demo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FileDownloadUtil {
    private static final Map<String, MediaType> SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(FileDownloadUtil.class);

    static {
        SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP.put(".pdf", MediaType.APPLICATION_PDF);
        SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP.put(".png", MediaType.IMAGE_PNG);
        SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP.put(".jpg", MediaType.IMAGE_JPEG);
        SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP.put(".jpeg", MediaType.IMAGE_JPEG);
    }

    private FileDownloadUtil() {
    }

    public static ResponseEntity<InputStreamResource> buildResponseEntity(
            String fileName, String path) {
        String suffix = FileUtil.getOriginSuffix(fileName);
        MediaType downloadFileMediaType = getDownloadFileMediaType(suffix);
        FileSystemResource urlResource = new FileSystemResource(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        //控制是否直接下载
        try {
            if (downloadFileMediaType == MediaType.APPLICATION_OCTET_STREAM) {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename*=utf-8''%s", URLEncoder.encode(fileName, "UTF-8")));
            } else {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename*=utf-8''%s", URLEncoder.encode(fileName, "UTF-8")));
            }
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(urlResource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(urlResource.getInputStream()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ResponseEntity.badRequest().build();
    }

    private static MediaType getDownloadFileMediaType(String fileSuffix) {
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (StringUtils.isBlank(fileSuffix)) {
            return mediaType;
        }

        fileSuffix = fileSuffix.toLowerCase();

        if (SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP.containsKey(fileSuffix)) {
            mediaType = SUPPORTED_VIEW_FILE_MEDIA_TYPE_MAP.get(fileSuffix);
        }

        return mediaType;
    }

    public static void configDownloadHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader(HttpHeaders.EXPIRES, "0");
        String timeStr = DateUtil.format(LocalDateTime.now(), DateUtil.YMD_STR_1);
        fileName = StringUtils.trimToEmpty(fileName).concat(SignConstant.UNDER_LINE).concat(timeStr).concat(".xlsx");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename*=utf-8''%s", URLEncoder.encode(fileName, "UTF-8")));
    }
}
