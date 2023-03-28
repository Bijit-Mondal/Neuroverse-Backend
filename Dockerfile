FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
# docker build --build-arg JAR_FILE=target/SQLite.demo-0.0.1-SNAPSHOT.jar -t neuroverse:latest .
# docker run -p 3000:8080 -d --name neuroverse bijitmondal/neuroverse:latest
# docker exec -it <container-id> sh
# docker tag neuroverse:latest bijitmondal/neuroverse:latest
# docker push bijitmondal/neuroverse:latest
ENTRYPOINT ["java","-jar","/app.jar"]