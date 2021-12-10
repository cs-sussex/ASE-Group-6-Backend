FROM openjdk:11-jre-slim

COPY ./target/ase-group-6-LATEST.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch ase-group-6-LATEST.jar'

ENTRYPOINT ["java","-jar","ase-group-6-LATEST.jar"]