FROM openjdk:17
VOLUME /temp
EXPOSE 8080
ADD ./target/gateway-service-0.0.1-SNAPSHOT.jar gateway-server.jar
ENTRYPOINT [ "java","-jar","/gateway-server.jar" ]