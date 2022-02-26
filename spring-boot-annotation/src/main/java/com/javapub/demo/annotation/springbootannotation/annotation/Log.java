package com.javapub.demo.annotation.springbootannotation.annotation;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/1/25 15:22
 * @Version: 1.0
 * @Description: #自定义日志注解。
 * <p>
 * ①：什么时候使用该注解，我们定义为运行时；
 * ②：注解用于什么地方，我们定义为作用于方法上；
 * ③：注解是否将包含在 JavaDoc 中；
 * ④：注解名为 Log;
 * ⑤：定义一个属性，默认为空字符串；
 */

import java.lang.annotation.*;


@Target(ElementType.METHOD) //注解用于什么地方，我们定义为作用于方法上；
@Retention(RetentionPolicy.RUNTIME) //什么时候使用该注解，我们定义为运行时；
@Documented //注解是否将包含在 JavaDoc 中；
public @interface Log {//注解名为Log

    String value() default ""; //定义一个属性，默认为空字符串；

}

