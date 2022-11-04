FROM openjdk:11
VOLUME /tmp
EXPOSE 8088
ADD ./target/ms-account-type-0.0.1-SNAPSHOT.jar ms-account-type.jar
ENTRYPOINT ["java","-jar","/ms-account-type.jar"]