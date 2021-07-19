FROM openjdk:11 
COPY target/*.jar /usr/src/komi-inbound.jar 
RUN mkdir -p /home/logs/inbound 
VOLUME /home/logs
WORKDIR /usr/src 
EXPOSE 9001
ENTRYPOINT ["java","-jar","/usr/src/komi-inbound.jar"]

# mvn clean package -DskipTests
# docker build -t fransmzh/komi-inbound:1.0 .
# docker push fransmzh/komi-inbound:1.0