FROM openjdk:11

VOLUME /tmp

COPY target/todo-backend.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "/app.jar"]