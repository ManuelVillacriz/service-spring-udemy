FROM openjdk:17
VOLUME /temp
EXPOSE 8092
ADD ./target/item-service-0.0.1-SNAPSHOT.jar item-service.jar
ENTRYPOINT [ "java","-jar","/item-service.jar" ]