#build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

#run
FROM amazoncorretto:21.0.5
WORKDIR /app
COPY --from=build ./build/target/*.jar ./app.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID='fasdfgasgsadfgsd'
ENV GOOGLE_CLIENT_SECRET='gsdfgsdgsdfgsdfgsd'
ENV SPRING_PROFILES_ACTIVE='default'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar app.jar
