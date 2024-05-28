FROM openjdk:21
EXPOSE 8081

ARG JAR_FILE=build/libs/reviews.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
