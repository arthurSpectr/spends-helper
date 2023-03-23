# Add databse configuration for application
FROM openjdk:11-jre-slim
VOLUME /tmp
COPY spehel-application/target/*.jar app.jar
EXPOSE 8089
CMD ["java","-jar","/app.jar"]
