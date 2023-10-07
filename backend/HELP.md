# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/#build-image)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#web.security)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#messaging.kafka)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#howto.batch)

### Guides
The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

## steps to run the application correctly:

* Install Docker and docker compose on your local machine (Docker Desktop contains both);

* pull a postgreSQL image and run the container (of course the choice of which relational db to use is free);

* install kafka on your local machine (I have followed this great tutorial https://betterdatascience.com/how-to-install-apache-kafka-using-docker-the-easy-way/?utm_content=cmp-true but I will riasssume the necessary steps here):
  * use the docker-compose.yml file in the repository to install and run the 2 necessary containers, apache kafka & apache zookeeper: launch a terminal with the command docker-compose -f docker-compose.yml up -d in the same folder of the .yml file;
  * connect to the kafka shell: docker exec -it kafka /bin/sh;
  * create a topic: kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic kafka_topic.


* The topic I have used are 3: 'create_topic', 'update_topic', 'delete_topic'. It's possible to create them within the IDE (Intellij & VsCode both have nice plugins for that) or with the command listed above.

All the infrastructure necessary to the application is up and running at this point.
Now it should be possible to launch the Spring Boot application without any issues.

* mvn clean install
* mvn spring-boot:run

Your Employee API is ready to use ^^

