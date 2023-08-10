FROM adoptopenjdk:11-jdk-hotspot

COPY  ./target/user-stock-preference.jar user-stock-preference.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "user-stock-preference.jar"]