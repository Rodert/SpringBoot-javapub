##基础镜像通过java8来的
#FROM java:8
##将当前文件中所有*.jar  拷贝到项目的app.jar中（这个app.jar是自己生成的）
#COPY *.jar /app.jar
##映射地址
#CMD ["--server.prot=8080"]
##暴露端口
#EXPOSE 8080
##执行命令java  -jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#支持中文字体的镜像，jdk8
#FROM ringcentral/jdk:latest

FROM openjdk:8-jre
ADD target/*.jar /application.jar
ENTRYPOINT ["java", "-jar","/application.jar"]
