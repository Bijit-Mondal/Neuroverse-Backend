FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
# docker build --build-arg JAR_FILE=target/SQLite.demo-0.0.1-SNAPSHOT.jar -t neuroverse:latest .
# docker run -p 8000:8080 -d -v myDB.db:/myDb?cache=shared --name neuroverse neuroverse:latest
# docker exec -it <container-id> sh
ENTRYPOINT ["java","-jar","/app.jar"]