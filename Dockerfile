
# build
#FROM gradle:8.7-jdk21 AS builder
#WORKDIR /workspace
#
#COPY . .
#RUN chmod +x gradlew
#RUN ./gradlew :module-api:bootJar --no-daemon


# run
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY --from=builder /workspace/module-api/build/libs/*.jar /app/deploy.jar
#ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/app/deploy.jar"]


FROM eclipse-temurin:21-jre
ARG JAR_FILE=/module-api/build/libs/*.jar
#https://bgpark.tistory.com/132
COPY ${JAR_FILE} /deploy.jar
ENV AWS_REGION=ap-northeast-2
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/deploy.jar"]