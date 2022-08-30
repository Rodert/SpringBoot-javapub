package com.javapub.excel.demo2.springbootexcelannotations.demo;

public class SignConstant {
    public static final String DEFAULT_SEPARATOR = ",";
    public static final String SHARP_SEPARATOR = "#";
    public static final String DOT_SEPARATOR = ".";
    public static final String BACK_SLASH_SEPARATOR = "/";
    public static final String EMPTY = "";
    public static final String UNDER_LINE = "_";
    public static final String HYPHEN = "-";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String EQUAL = "=";
    public static final String LEFT_SQUARE_BRACKET = "[";
    public static final String RIGHT_SQUARE_BRACKET = "]";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String ANY = "*";

    /**
     * 查询模糊度正负分分解线
     */
    public static final Integer FUZZY_POINT = 20;
    public static final String DEFAULT_SEP = "&&";
    /**
     * 用户企业信息在redis存储时key后缀
     */
    public static final String USER_TENANT_REDIS_IDEN = "_tenantInfo";
    public static final String USER_TENANT_MAP = "_userTenantMap";
    public static final String USER_TENANTID_KEY = "tenantId";
    public static final String USER_USERID_KEY = "userId";
    public static final String TENANT_SPECIAL_CHAR = "_tenantSpecialChar";

    private SignConstant() {
    }
}
