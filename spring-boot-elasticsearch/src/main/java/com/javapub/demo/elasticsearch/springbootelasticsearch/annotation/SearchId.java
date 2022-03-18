package com.javapub.demo.elasticsearch.springbootelasticsearch.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchId {
}
