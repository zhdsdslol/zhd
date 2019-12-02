FROM java:8
VOLUME /tmp
ADD server-movies-0.0.1-SNAPSHOT.jar movies.jar
RUN bash -c 'touch /movies.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/movies.jar"]