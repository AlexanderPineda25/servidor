FROM amazoncorretto:22-alpine-jdk
EXPOSE 8080
COPY target/vanguardia-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]