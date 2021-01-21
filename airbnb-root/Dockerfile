FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY . /build/
WORKDIR /build/
RUN mvn package

FROM openjdk:8-jre-alpine
WORKDIR /app
RUN ls
COPY --from=MAVEN_BUILD /build/server/target/server-0.0.1-SNAPSHOT.jar /app/