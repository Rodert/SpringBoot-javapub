package com.javapub.spark.demo.javasparkdemo.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

/**
 * author:
 */
public class SparkDemo {

    public static void main(String[] args) {
        // 解决:在这个位置添加如下配置信息即可
//        System.setProperty("hadoop.home.dir", "D:\\software\\hadoop-2.10.1\\hadoop-2.10.1");

        String readme = "E:\\myworkpace\\SpringBoot-javapub\\java-spark-demo\\src\\main\\resources\\CHANGES.txt";
        SparkConf conf = new SparkConf().setAppName("tiger's first spark app");
        conf.setMaster("local[2]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        // 从指定的文件中读取数据到RDD
        JavaRDD<String> logData = sc.textFile(readme).cache();

        // 过滤包含h的字符串，然后在获取数量
        long num = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s.contains("h");
            }

        }).count();

        System.out.println("the count of word a is " + num);

    }
}