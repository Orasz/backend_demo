# backend_demo
Demo Project for practice


![overview drawio (2)](https://github.com/Orasz/backend_demo/assets/26349651/0c0f6ec6-d42e-4271-912b-192fe66d68f1)

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/maven-plugin/reference/html/)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/index.html#messaging.kafka)


### Guides
The following guides illustrate how to use some features concretely:

* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## steps to run the application correctly:

* Install Docker and docker compose on your local machine (Docker Desktop contains both);

* pull a postgreSQL image and run the container (of course the choice of which relational db to use is free);

* install kafka on your local machine (I have followed this great tutorial https://betterdatascience.com/how-to-install-apache-kafka-using-docker-the-easy-way/?utm_content=cmp-true but I will riasssume the necessary steps here):
  * use the docker-compose.yml file in the repository to install and run the 2 necessary containers, apache kafka & apache zookeeper: launch a terminal with the command docker-compose -f docker-compose.yml up -d in the same folder of the .yml file;
  * connect to the kafka shell: docker exec -it kafka /bin/sh;
  * create a topic: kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic kafka_topic.


* The topic I have used are 4: 'create_topic', 'update_topic', 'delete_topic', 'employee-events'.
* It's possible to create them within the IDE (Intellij & VsCode both have nice plugins for that) or with the command listed above.

All the infrastructure necessary to the application is up and running at this point.
Now it should be possible to launch the Spring Boot application without any issues.

* mvn clean install
* mvn spring-boot:run

Your Employee API is ready to use ^^

## next steps:
* improve test suite (Kafka module)
* add a scheduler to delete the employees who resigned (instead of dumb actual implementation)
* secure the API with Spring Security
* Enrich and optimise data model (use an explicit join table and 2 one-to-many relationships instead of one many-to-many)
* Implementation of standalone hobby API


