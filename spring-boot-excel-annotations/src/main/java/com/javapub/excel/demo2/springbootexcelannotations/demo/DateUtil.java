package com.javapub.excel.demo2.springbootexcelannotations.demo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Created by mcc on 2016/12/17.
 */
public class DateUtil {
    public static final String YMD_HMS_STR_POINT = "yyyy-MM-dd-HH-mm-ss";
    public static final String YMD_HMS_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_HM_STR = "yyyy-MM-dd HH:mm";
    public static final String YMD_STR = "yyyy-MM-dd";
    public static final String YMD_STR_1 = "yyyyMMdd";
    public static final String YM_STR = "yyyy-MM";
    public static final DateTimeFormatter YMD_FORMATTER = new DateTimeFormatterBuilder().appendPattern(YMD_STR)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();
    public static final DateTimeFormatter YM_FORMATTER = new DateTimeFormatterBuilder().appendPattern(YM_STR)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();
    public static final DateTimeFormatter YMD_HMS_FORMATTER = DateTimeFormatter.ofPattern(YMD_HMS_STR);
    public static final DateTimeFormatter YMD_HM_FORMATTER = DateTimeFormatter.ofPattern(YMD_HM_STR);

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
    }

    public static String format(LocalDateTime date) {
        return formatByFormatter(date, YMD_HMS_FORMATTER);
    }

    public static String format(LocalDateTime date, String format) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatByFormatter(date, formatter);
    }

    private static String formatByFormatter(LocalDateTime date, DateTimeFormatter dateTimeFormatter) {
        String ret = null;

        if (date == null || dateTimeFormatter == null) {
            return ret;
        }
        try {
            ret = date.format(dateTimeFormatter);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return ret;
    }

    public static LocalDateTime parse2LocalDate(String dateStr) {
        return parseByFormatter(dateStr, YMD_FORMATTER);
    }

    public static LocalDateTime parse2LocalDateTime(String dateStr) {
        return parseByFormatter(dateStr, YMD_HMS_FORMATTER);
    }

    public static LocalDateTime parseByFormatter(String dateStr, DateTimeFormatter dateFormatter) {
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dateFormatter);
                return localDateTime;

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static long toMilliseconds(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static boolean isBefore(LocalDateTime source, LocalDateTime target) {
        if (source == null) {
            return false;
        }
        return source.compareTo(target) < 0;
    }
}
