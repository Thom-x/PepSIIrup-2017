FROM openjdk:8
VOLUME /tmp
ADD target/com.SII.EventService-1.1.0.RELEASE.jar app.jar
RUN sh -c 'touch /app.jar'
RUN echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
