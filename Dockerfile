FROM eclipse-temurin:17-jre
LABEL authors="salaheddine"

WORKDIR app

COPY target/patient-management-0.0.1-SNAPSHOT.jar patient-management-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "patient-management-0.0.1-SNAPSHOT.jar","--spring.profiles.active=${ENV_PROFILE}"]