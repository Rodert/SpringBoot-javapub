图文并茂

# 工程简介


SpringBoot2.x整合Prometheus+Grafana【附源码】

附源码+视频

## SpringBoot工程初始化

springboot加速初始化：https://start.aliyun.com/

![image](https://tvax2.sinaimg.cn/large/007F3CC8ly1h38qosts5ej30yr0g978g.jpg)

**添加依赖**

pom.xml

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

**配置信息**

在application.yml增加以下配置项

```yml

##SpringBoot2.x整合Prometheus+Grafana
##源码：https://github.com/Rodert/SpringBoot-javapub
management:
  metrics:
    export:
      prometheus:
        enabled: true
        step: 1m
        descriptions: true
  web:
    server:
      auto-time-requests: true
  endpoints:
    prometheus:
      id: springmetrics
    web:
      exposure:
        include: health,info,env,prometheus,metrics,httptrace,threaddump,heapdump,springmetrics
server:
  port: 8080

```

**启动SpringBoot应用**

http://localhost:8080/actuator/prometheus 

打开即可看到暴露的信息

![image](https://tva3.sinaimg.cn/large/007F3CC8ly1h38qskayrtj31h10rvnah.jpg)

## 环境安装

如安装包下载不成功，可以在公众号回复【prometheus安装包】or【grafana安装包】领取

### Prometheus安装

下载地址：https://prometheus.io/download/

![image](https://tvax4.sinaimg.cn/large/007F3CC8ly1h38oxcocowj31ha0rn4d2.jpg)

**修改配置**：

`prometheus.yml`

```yml
# my global config
global:
  scrape_interval:     15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.
    #暴露路径
    metrics_path: /actuator/prometheus
    static_configs:
    #SpringBoot的ip和端口号
    - targets: ['localhost:8080']
```

**启动Prometheus**

prometheus.exe

![image](https://tva4.sinaimg.cn/large/007F3CC8ly1h38pcatv96j319o0d7n3h.jpg)

**测试访问**

<http://localhost:9090>

 ![image](https://tvax3.sinaimg.cn/large/007F3CC8ly1h38pdbxgyej31ha0qcn3i.jpg)

jvm_memory_used_bytes

![image](https://tva3.sinaimg.cn/large/007F3CC8ly1h38perncz4j31hc0ry157.jpg)

### Grafana安装

下载地址：https://mirrors.huaweicloud.com/grafana/

![image](https://tvax4.sinaimg.cn/large/007F3CC8ly1h38p3gvdgkj31ha0rv48u.jpg)

**启动grafana**

![image](https://tva4.sinaimg.cn/large/007F3CC8ly1h38p5d05cmj31cb0ew43b.jpg)

**测试**

<http://127.0.0.1:3000/login>

![image](https://tvax4.sinaimg.cn/large/007F3CC8ly1h38p6jd0jtj31hb0qeh8s.jpg)

默认账号：admin 密码：admin

![image](https://tva3.sinaimg.cn/large/007F3CC8ly1h38p7dmbhij31ha0rx1cd.jpg)

## 整合

**增加数据源**

![image](https://tvax4.sinaimg.cn/large/007F3CC8ly1h38ph075bkj31h80s1av4.jpg)

![image](https://tvax2.sinaimg.cn/large/007F3CC8ly1h38phfsxtfj31hc0s4k0v.jpg)

- Name填一个
- URL填的Prometheus访问地址

![image](https://tva2.sinaimg.cn/large/007F3CC8ly1h38plo6vi4j31h70rojzl.jpg)

**添加图表**

![image](https://tvax4.sinaimg.cn/large/007F3CC8ly1h38pom4a67j31h70rz1co.jpg)

![image](https://tva1.sinaimg.cn/large/007F3CC8ly1h38pp7vvomj31h90rxdsj.jpg)

指定数据源、指定监控指标 jvm_memory_used_bytes

![image](https://tva2.sinaimg.cn/large/007F3CC8ly1h38q9o2hfjj31h30s116h.jpg)

切换图标

![image](https://tvax3.sinaimg.cn/large/007F3CC8ly1h38qczr24tj31ha0rw153.jpg)

![image](https://tvax1.sinaimg.cn/large/007F3CC8ly1h38qfbxz0tj31h90s2dta.jpg)

源码地址：https://github.com/Rodert/SpringBoot-javapub

# 延伸阅读

1. [SpringBoot自定义注解](https://gitee.com/rodert/SpringBoot-javapub/tree/main/spring-boot-annotation)
2. [SpringBoot整合docker入门](https://gitee.com/rodert/SpringBoot-javapub/tree/main/spring-boot-docker)
3. [SpringBoot整合ElasticSearch](https://gitee.com/rodert/SpringBoot-javapub/tree/main/spring-boot-elasticsearch)
4. [SpringBoot快速整合Excel](https://gitee.com/rodert/SpringBoot-javapub/tree/main/spring-boot-excel)
5. [SpringBoot整合MyBatis-支持批量更新](https://gitee.com/rodert/SpringBoot-javapub/tree/main/spring-boot-mybatis)
6. [SpringBoot实现链路追踪spring-boot-trace](https://gitee.com/rodert/SpringBoot-javapub/tree/main/spring-boot-trace)


## 中级篇

1. [手把手整合SSM-Spring-Spring MVC-Mybatis](https://gitee.com/rodert/SpringBoot-javapub/tree/main/ssm_helloworld_web)

## 实战篇

1. [通用后台管理系统](https://gitee.com/rodert/liawan-vue)

> 将支持：Activiti + Flowable 工作流； 第三方登录； 支付； 短信； 支持 RBAC 动态权限、数据权限；监接；商城。SpringBoot Spring Security JWT MyBatis Druid Vue Vuex Element-ui Axios Sass Quill docker-compose、Kafka

