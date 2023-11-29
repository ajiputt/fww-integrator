FROM openjdk:17-jdk
ARG JAR_FILE=target/fww-integrator-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} fww-integrator-1.0.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar","/fww-integrator-1.0.jar"]