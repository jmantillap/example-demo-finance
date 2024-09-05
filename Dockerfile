FROM openjdk:17-jdk-slim

#host.docker.internal --> maquina fisica donde esta la base de data. host anfitrion
ENV JDBC_URL_BD=jdbc:mysql://host.docker.internal:3306/demo-finance
ENV USERNAME_BD=root
ENV PASSWORD_BD=root

COPY target/example-demo-finance-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

#$ docker build finance-app:v1 .
#$ docker run -it --name fiance-contenedor-app -p 8080:8080 finance-app:v1
#$ docker run -it --rm --name fiance-contenedor-app -p 8080:8080 finance-app:v1
