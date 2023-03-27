FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./target/chatgptbot-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "chatgptbot-0.0.1-SNAPSHOT.jar"]