保姆级教程、偏应用 JavaPub

Deeplearning4J就实现了word2Vec。https://github.com/deeplearning4j/dl4j-examples.git

[toc]

**环境**

基于CentOS7

### 下载&编译

因为网络问题，我在Gitee克隆了一份。

> git clone https://gitee.com/rodert/word2vec.git

![image](https://tva2.sinaimg.cn/large/007F3CC8ly1h15v77yx5vj30jj089tep.jpg)

### 执行初始化脚本（速度可能较慢）：

```bash
cd word2vec

make

cd scripts

sh demo-word.sh
```

大概执行了以下三步：

1. 从http://mattmahoney.net/dc/text8.zip 下载了一个文件text8 ( 一个解压后不到100M的txt文件，可自己下载并解压放到同级目录下)；
2. 使用文件text8进行训练，训练过程比较长；
3. 执行word2vec生成词向量到 vectors.bin文件中，（速度比较快，几分钟的事情）

demo-word.sh 调用了 create-text8-vector-data.sh，如下命令：

> time ./word2vec -train text8 -output vectors.bin -cbow 1 -size 200 -window 8 -negative 0 -hs 1 -sample 1e-4 -threads 20 -binary 1 -iter 15

命令解析：

```bash
-train text8 表示的是输入文件是text8

-output vectors.bin 输出文件是vectors.bin

-cbow 1 表示使用cbow模型，默认为Skip-Gram模型

-size 200 每个单词的向量维度是200

-window 8 训练的窗口大小为5就是考虑一个词前八个和后八个词语（实际代码中还有一个随机选窗口的过程，窗口大小小于等于8）

-negative 0 -hs 1不使用NEG方法，使用HS方法。

-sampe 指的是采样的阈值，如果一个词语在训练样本中出现的频率越大，那么就越会被采样。

-binary为1指的是结果二进制存储，为0是普通存储（普通存储的时候是可以打开看到词语和对应的向量的）

-iter 15 迭代次数
```

#### 运行命令 

> ./demo-phrases.sh

查看该脚本内容，主要执行以下步骤：

从http://www.statmt.org/wmt14/training-monolingual-news-crawl/news.2012.en.shuffled.gz 下载了一个文件news.2012.en.shuffled.gz ( 一个解压到1.7G的txt文件，可自己下载并解压放到同级目录下); 将文件中的内容拆分成 phrases，然后执行./word2vec生成短语向量到 vectors-phrase.bin文件中（数据量大，速度慢，将近半个小时），如下：

最后一行命令./distance vectors-phrase.bin，一个计算word相似度的demo中去，结果如下：

结果好坏跟训练词库有关 。

