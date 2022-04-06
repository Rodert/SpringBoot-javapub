package com.javapub.demo.elasticsearch.springbootelasticsearch.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseDao<T> {
    //代表的是某个实体的类型
    private Class<T> entityClass;

    public BaseDao() {
        //getClass()获得一个实例的类型类;相当于 某类.class eg:UserDao.class
        Class class1 = getClass();
        System.out.println(class1);

        //getSuperclass()获得该类的父类
        Class superclass = class1.getSuperclass();
        System.out.println(superclass);

        //getGenericSuperclass()获得带有泛型的父类
        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型
        Type type = class1.getGenericSuperclass();
        System.out.println(type);

        //ParameterizedType参数化类型，即泛型
        ParameterizedType p = (ParameterizedType) type;
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        entityClass = (Class<T>) p.getActualTypeArguments()[0];
        System.out.println(entityClass);

    }
}
