FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
COPY target/AwsDemo-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]