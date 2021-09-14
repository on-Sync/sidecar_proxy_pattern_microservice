# ---------------------------------------------arguments

ARG APP
ARG PROFILE=${PROFILE:-stage}
ARG VERSION=${VERSION:-*}

# ---------------------------------------------builder

FROM maven as builder
ARG APP
ARG PROFILE
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
COPY --chown=maven:maven . /home/maven/src
WORKDIR /home/maven/src
COPY resource/keystore/EncryptionKey.jks ${APP}/src/main/resources/keystore/EncryptionKey.jks
RUN mvn clean package -f pom.xml -pl ${APP} -am

# ---------------------------------------------deployment

FROM openjdk:17-ea-11-jdk-slim as deployment
ARG APP
ARG PROFILE
ARG VERSION
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
VOLUME [ "/data" ]
RUN mkdir /app
COPY --from=builder /home/maven/src/${APP}/target/*-${VERSION}.jar /app/spring-boot-application.jar
RUN echo "SPRING_PROFILES_ACTIVE : ${SPRING_PROFILES_ACTIVE}"
ENTRYPOINT [ "java", "-jar", "/app/spring-boot-application.jar"]