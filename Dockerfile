FROM openjdk:11
ADD /build/libs/spring-rest-api-0.0.1-SNAPSHOT.jar springREST.jar
ENTRYPOINT ["java",  "-jar", "springREST.jar"]