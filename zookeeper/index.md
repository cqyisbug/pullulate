Zookeeper
===


## 整体架构
![架构图](zookeeper_structure.jpg)  
| 部分 | 说明 |
|---|---|
| Client | zookeeper的管理对象,从服务器中访问信息.与服务器会保持一个连接 |
| Server | Zookeeper节点,又称之为**znode**,提供服务 |
| Ensemble | 服务器组 |
| Leader | 服务器节点,在服务启动时选举产生 |
| Follower | 跟随leader指令的服务器节点 |

