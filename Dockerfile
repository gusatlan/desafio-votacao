FROM openjdk:8-jre
RUN apt update;apt -y upgrade;apt -y autoclean;apt -y autoremove

EXPOSE 8080
WORKDIR /tmp
ADD /target/votacao*.jar /tmp/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
