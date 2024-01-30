FROM openjdk:17

EXPOSE 8080
ADD build/libs/fiap-fastfood-*.jar /opt/api.jar
ENTRYPOINT exec java $JAVA_OPTS $APPDYNAMICS -jar /opt/api.jar

