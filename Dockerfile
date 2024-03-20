# syntax=docker/dockerfile:1
FROM maven:3.9.6-eclipse-temurin-17-alpine
RUN mkdir /build
COPY . /build
WORKDIR /build
RUN mvn clean package -DskipTests
WORKDIR /build/target
RUN mv *.jar ManageTodoApp.jar
WORKDIR /
RUN mkdir /app
RUN mv /build/target/ManageTodoApp.jar /app/ManageTodoApp.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","ManageTodoApp.jar"]
# CMD [ "/bin/sh" ]
# ## Build stage#
# FROM maven:3.9.6-eclipse-temurin-21 AS builder
# RUN mkdir /build
# COPY . /build
# WORKDIR /build
# RUN mvn clean package -DskipTests
# ## Package stage#
# FROM eclipse-temurin:21-jdk
# RUN mkdir /app
# COPY --from=builder /build/target/service-1.0-SNAPSHOT.jar /app/service.jar
# WORKDIR /app
# CMD ["java","-jar","service.jar"]
