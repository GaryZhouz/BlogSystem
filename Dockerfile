FROM java:8

RUN mkdir -p /opt/blog/

COPY ./target/blog-0.0.1-SNAPSHOT.jar /opt/blog/

COPY run.sh /opt/blog/

EXPOSE 8080

WORKDIR /opt/blog

ENTRYPOINT ["sh", "/opt/blog/run.sh"]
