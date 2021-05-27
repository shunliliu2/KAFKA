# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/#build-image)

发布配置命令：
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=hxocr-frontsystem&group=DEFAULT_GROUP&content=useLocalCache=true"
查询配置命令:
curl http://localhost:8080/config/get

发布服务命令：
curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=example&ip=127.0.0.1&port=8080'
查询服务命令：
curl http://localhost:8080/discovery/get?serviceName=example

