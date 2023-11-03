FROM openjdk:17
VOLUME /temp
EXPOSE 8761
ADD ./target/eureka-service-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT [ "java","-jar","/eureka-server.jar" ]