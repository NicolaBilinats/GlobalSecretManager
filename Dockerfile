FROM java
MAINTAINER Nicola Bilinac 'tessariman@gmail.com'
#COPY . /app
RUN apt-get update && apt-get install -y --no-install-recommends \
                git \
                maven \
        && rm -rf /var/lib/apt/lists/*
WORKDIR globalsecretmanager
#RUN git clone https://github.com/spring-guides/gs-spring-boot.git
ADD pom.xml ./
RUN mvn package
FROM java
COPY java /globalsecretmanager/target/globalsecretmanager-0.0.1.jar ./

EXPOSE 8080

CMD [ "java", "-jar", "globalsecretmanager-0.0.1.jar" ]