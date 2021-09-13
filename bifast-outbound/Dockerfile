FROM adoptopenjdk:11-jre-hotspot 
COPY target/*.jar /usr/src/komi-outbound.jar 
RUN mkdir -p /home/logs
VOLUME /home/logs
WORKDIR /usr/src 
EXPOSE 9002
ENTRYPOINT ["java","-jar","/usr/src/komi-outbound.jar"]

# mvn clean package -DskipTests
# docker build -t fransmzh/komi-outbound:1.3 .
# docker push fransmzh/komi-outbound:1.3