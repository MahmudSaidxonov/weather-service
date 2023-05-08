#FROM openjdk:17
#EXPOSE 8080
#ADD target/weather-service-0.0.1-SNAPSHOT.jar weather-service.jar
#ENTRYPOINT ["java", "-jar", "/weather-service.jar"]





#COPY ./target/weather-service-0.0.1-SNAPSHOT.jar weather-service.jar