package com.javapub.demo.elasticsearch.springbootelasticsearch.annotation;

import java.lang.annotation.*;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/4/5 20:56
 * @Version: 1.0
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchId {
}
