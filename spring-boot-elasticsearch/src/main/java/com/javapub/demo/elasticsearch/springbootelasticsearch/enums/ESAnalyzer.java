package com.javapub.demo.elasticsearch.springbootelasticsearch.enums;

/**
 * 分词类型
 */
public enum ESAnalyzer {
    DEFAULT,
    STANDARD,
    PATTERN,
    RAW_LOWER,
    PINYIN,
    PINYIN_RAW,
    MMSEG_MAXWORD,
    MY_EMAIL_ANALYZER,
    MY_URL_ANALYZER,
    MY_DIGIT_EDGE_NGRAM_ANALYZER_16,
    MY_NGRAM_ANALYZER_16
}
