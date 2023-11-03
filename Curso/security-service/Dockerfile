FROM openjdk:17
VOLUME /temp
EXPOSE 9100
ADD ./target/springboot-servicio-oauth-0.0.1-SNAPSHOT.jar security-service.jar
ENTRYPOINT [ "java","-jar","/security-service.jar" ]