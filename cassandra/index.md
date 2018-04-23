Cassandra
===

## 特性
### 一致性
什么叫数据库的一致性？读操作一定会返回最新写入的结果。  
**Cassandra是最终一致性**（弱一致性）：成功写入后，读取的并**不一定是最新**数据，但过一段时间（毫秒级别，跨机房时间会更长）所有副本才会达成一致。  
Cassandra是最终一致性**原因**：优化写入性能，支持ONE、Qurum、ALL等.  
Cassandra支持一致性调节：当要求成功写入节点数与副本数一致时，即ALL时，认为是强一致性的。

### CAP理论
CAP理论指出在一个分布式系统中，你只能强化其中两个方面 
- Consistent：一致性，每次读取都是最新的数据 
- Available：可用性，客户端总是可以读写数据 
- Partition Tolerant：分区耐受性，数据库分散到多台机器，即使某台机器故障，也可以提供服务

### 编程驱动

DataStax Java Driver for Apache Cassandra是Apache Cassandra的一个Java驱动。它支持Cassandra Query Language version 3（CQL3）和Cassandra的二进制协议。它主要包括以下模块。 
- driver-core：核心层 
- driver-mapping：对象映射 
- driver-extras：JAVA驱动的可选特性 
- driver-examples 
- driver-tests 

## 使用
略...

## 安装
### 单节点

### 集群模式
