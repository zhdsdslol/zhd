FROM java:8
VOLUME /tmp
ADD server_zuul-0.0.1-SNAPSHOT.jar zuul.jar
RUN bash -c 'touch /zuul.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zuul.jar"]