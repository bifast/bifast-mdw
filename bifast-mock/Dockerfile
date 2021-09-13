FROM adoptopenjdk:11-jre-hotspot 
COPY target/*.jar /usr/src/komi-mock.jar 
WORKDIR /usr/src 
EXPOSE 9006
ENTRYPOINT ["java","-jar","/usr/src/komi-mock.jar"]

# mvn clean package -DskipTests
# docker build -t fransmzh/komi-mock:1.2 .
# docker push fransmzh/komi-mock:1.2