FROM openjdk:8
USER root

WORKDIR /app
COPY ./casadoagricultor-api-web-service/target/*.jar app.jar

ENV PORT=8080
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE $PORT