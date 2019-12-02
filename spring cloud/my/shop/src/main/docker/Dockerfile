FROM java:8
VOLUME /tmp
ADD shop-0.0.1-SNAPSHOT.jar shop.jar
RUN bash -c 'touch /shop.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/shop.jar"]