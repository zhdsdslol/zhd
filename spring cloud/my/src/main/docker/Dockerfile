FROM java:8
VOLUME /tmp
ADD my-0.0.1-SNAPSHOT.jar my.jar
RUN bash -c 'touch /my.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/my.jar"]