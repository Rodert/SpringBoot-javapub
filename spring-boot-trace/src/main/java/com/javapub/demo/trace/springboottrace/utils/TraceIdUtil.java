package com.javapub.demo.trace.springboottrace.utils;

import java.util.UUID;

public class TraceIdUtil {

    public static String getTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}
