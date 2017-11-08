FROM java
MAINTAINER Nicola Bilinac 'tessariman@gmail.com'
#COPY . /app
WORKDIR globalsecretmanager/initial
RUN apt-get update && apt-get install -y --no-install-recommends \
                git \
                maven \
        && rm -rf /var/lib/apt/lists/*
EXPOSE 8080

CMD [ "java", "-jar", "globalsecretmanager-0.1.0.jar" ]