FROM openjdk:23-jdk-oracle AS builder

WORKDIR /compileddir

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true
# RUN ./mvnw package -Dmaven.test.skip=true

ENV SERVER_PORT=8080 
EXPOSE ${SERVER_PORT}

#optional if multi stage
# ENTRYPOINT SEVER_PORT=${SERVER_PORT} java -jar target/inclass-0.0.1-SNAPSHOT.jar

FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app
WORKDIR ${WORK_DIR}

#first arg from entrypoint above, 2nd arg name it anything.jar
COPY --from=builder /compileddir/target/workshop-0.0.1-SNAPSHOT.jar anything.jar

ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}
#bring down from copy line (2nd arg)
ENTRYPOINT java -jar anything.jar

HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 CMD curl -s -f http://localhost:4000/demo/health || exit 1

#when you docker image build, use the correct tag

