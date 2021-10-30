## Starting kafka manually

1) Download kafka+zookeeper from
https://www.apache.org/dyn/closer.cgi?path=/kafka/3.0.0/kafka_2.13-3.0.0.tgz
2)
```shell
tar -xzf kafka_2.13-3.0.0.tgz
cd kafka_2.13-3.0.0
```
3) Change the server.config:
```
advertised.listeners=PLAINTEXT://your.host.name:9094
```
4) 
```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```