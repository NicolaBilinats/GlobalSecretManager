
FROM dedjopa/docker-builder AS builder

ADD ./ /globalsecretmanager
WORKDIR /globalsecretmanager
RUN bower install
RUN mvn package

FROM openjdk:jre-alpine

MAINTAINER Nicola Bilinac 'tessariman@gmail.com'
COPY --from=builder /globalsecretmanager/target/globalsecretmanager-0.0.1-SNAPSHOT.jar /

EXPOSE 8080

CMD [ "java", "-jar", "/globalsecretmanager-0.0.1-SNAPSHOT.jar" ]