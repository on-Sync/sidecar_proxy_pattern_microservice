# ---------------------------------------------arguments

ARG APP
ARG PROFILE=${PROFILE:-stage}
ARG VERSION=${VERSION:-*}
ARG CI=${CI:-docker}

# ---------------------------------------------builder

FROM gradle:7-jdk11 as builder
ARG APP
ARG PROFILE
ARG CI
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
ENV CI=${CI}
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
COPY resource/keystore/EncryptionKey.jks ${APP}/src/main/resources/keystore/EncryptionKey.jks
RUN ./gradlew build -p ${APP}

# ---------------------------------------------deployment

FROM openjdk:17-ea-11-jdk-slim as deployment
ARG APP
ARG PROFILE
ARG VERSION
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
VOLUME [ "/data" ]
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/*-${VERSION}.jar /app/spring-boot-application.jar
RUN echo "SPRING_PROFILES_ACTIVE : ${SPRING_PROFILES_ACTIVE}"
ENTRYPOINT [ "java", "-jar", "/app/spring-boot-application.jar"]