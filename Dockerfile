FROM openjdk:9-jdk-slim AS builder

RUN apt-get update && apt-get install -y --no-install-recommends \
                git \
                maven \
        && rm -rf /var/lib/apt/lists/*

WORKDIR globalsecretmanager/initial
ADD pom.xml ./
RUN mvn package

FROM openjdk:9-jre-slim
COPY --from=builder /globalsecretmanager/initial/target/globalsecretmanager-0.1.0.jar ./
CMD ["java", "-jar", "globalsecretmanager-0.1.0.jar"]
EXPOSE 8080