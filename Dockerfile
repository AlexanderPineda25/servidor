FROM amazoncorretto:22-alpine-jdk

COPY target/vanguardia-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]