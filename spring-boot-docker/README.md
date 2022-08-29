# docker 打包 springboot 项目快速入门
`文末源码`
@[toc]

# 1.构建Sringboot工程
### 新建项目
![在这里插入图片描述](https://img-blog.csdnimg.cn/0b8fd4ff98d14221bffd72399422802d.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/60bfb836198a407dadeb995ceb7acdc7.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/555e9620d7cb44a6a13b4f1db856ac07.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/d668ec419674475582eb232660d3a53f.png)
### 创建接口
```java
package com.wangshiyu.javapub.demo.springbootdocker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/2/26 12:24
 * @Version: 1.0
 * @Description:
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    String hello(@RequestParam(required = false, defaultValue = "") String name) {
        return "hello, welcome wangshiyu JavaPub " + name;
    }
}

```

### maven 打包 springboot 项目

```bash
mvn clean package -DskipTests
```

# 2.编写Dockerfile

Dockerfile讲解

```bash
#基础镜像通过java8来的
FROM java:8
#将当前文件中所有*.jar  拷贝到项目的app.jar中（这个app.jar是自己生成的）
COPY *.jar /app.jar
#映射地址
CMD ["--server.prot=8080"]
#暴露端口
EXPOSE 8080
#执行命令java  -jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

# 3.docker环境搭建和构建docker镜像运行
### 文件准备
拷贝 *.jar 包和 Dockerfile 到linux服务器文件夹下

### linux下docker安装
本次在root权限下安装

1. 更新yum到最新版本

> yum update

2. 卸载旧版本（如果没安装，忽略）

> yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine

> yum list installed | grep docker

> yum remove docker-ce

> rm -rf /var/lib/docker

curl -sSL https://get.daocloud.io/docker | sh

2. 安装一些工具

> yum install -y yum-utils device-mapper-persistent-data lvm2

> yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

3. 安装docker稳定版

> yum install docker-ce 

4. 成功

> docker version

#### 异常
如果中途出现什么旧版本没卸载干净，把卸载命令全部重新执行一遍。

### 打包构建docker镜像

1. 将 jar包 打包成 docker 镜像（基于Dockerfile打包一个名为 idea_docker_image_test 的镜像，版本为1.0）

> docker build -t idea_docker_image_test:1.0 -f Dockerfile .

2. 查看镜像是否打包成功

> docker images

### 运行
> docker run -d --name idea_docker_image_test -p 8080:8080 idea_docker_image_test:1.0

![在这里插入图片描述](https://img-blog.csdnimg.cn/5f5904f4f93f485195eee3810bd55d89.png)


### 查看日志

1. 查看控制台日志

> docker logs -f --tail=100 容器id

2. 查看slf4j日志

> 通过 docker exec -it 容器id /bin/bash 进入容器
再通过项目里指定的路径及可找到日志文件

# 仓库地址

公众号: JavaPub 回复： **仓库地址**

![在这里插入图片描述](https://img-blog.csdnimg.cn/54ec6ca216984f7d9fc74b9bad8e3240.png)


---

---

---

# springboot部署到k8s

主要有这么几个步骤：

1. 完成应用代码的编写

2. 把程序打包成容器镜像

3. 使用上一步打包的镜像，创建应用的Pod

4. 用 Deployment 调度应用

5. 使用 Service 暴露应用

6. 通过 Ingress 代理应用

好了，这六步大家一定要记住，下面我们逐一展开，详细说说。


