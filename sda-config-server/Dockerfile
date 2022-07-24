FROM amazoncorretto:17-alpine
RUN mkdir /opt/app
COPY *.jar /opt/app/app.jar
CMD ["java", "-jar", "ls ~/.ssh && ls /etc/secret-volume && /opt/app/app.jar"]
