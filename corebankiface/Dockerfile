FROM adoptopenjdk:11-jre-hotspot 
COPY target/corebankiface-0.1-SNAPSHOT.jar /usr/src/mockcb.jar 
WORKDIR /usr/src 
EXPOSE 9010
ENTRYPOINT exec java $JAVA_OPTS -jar /usr/src/mockcb.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar bifast.jar
