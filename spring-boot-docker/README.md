https://blog.csdn.net/qq_39497653/article/details/110487635


maven 打包 springboot 项目

mvn clean package -DskipTests

拷贝 *.jar 包和 Dockerfile 到linux服务器文件夹下

> Linux 下 docker 安装方式

将 jar包 打包成 docker 镜像（基于Dockerfile打包一个名为idea_docker_test的镜像，版本为1.0）

docker build -t idea_docker_test:1.0 -f Dockerfile .

查看镜像是否打包成功

docker images

