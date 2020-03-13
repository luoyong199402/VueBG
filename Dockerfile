FROM openjdk:8u242

WORKDIR /home

add demo-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar;"]