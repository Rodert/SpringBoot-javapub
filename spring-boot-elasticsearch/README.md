[toc]

# elasticsearch 整合 springboot 新闻搜索

1. 携带es用户名密码

# 功能

### 文档类

获取文档 删除文档 更新文档 批量增删改 根据查询删除 根据查询更新 重建索引 词组分析，只能针对一个文档 多个文档的词组分析

#### 搜索类

搜索建议

### 索引类

增删查改 设置索引

### 别名类

增删查改


---

# 测试

1. 启动
2. 请求初始化索引
   1. http://127.0.0.1:8081/internal/init?type=news



---

# head可视化插件安装

http://mobz.github.io/elasticsearch-head/

# 附加阅读

1. 配置文件加载顺序，properties->yml，加载读取到配置则停止。

# 参考

https://blog.csdn.net/datuanyuan/article/details/109143900

https://github.com/datuanyuan/search.git

# 计划

1. 初始化springboot（全局返回值）
2. 构建数据的增删查改
3. 定时采集并提取写入一部分数据
4. word2vec 接入并做简单使用
5. 接入全链路日志
6. 接入监控，整合普罗米修斯
7. 实现每秒写入大批量数据的一些优化
8. 实现切面日志
9. 打包docker部署