FROM maven:3-jdk-11-slim AS build

ARG GITHUB_TOKEN
ARG INTAKE

WORKDIR /usr/app

COPY poms/releases.pom.xml settings.xml ./

RUN mvn dependency:go-offline \
    -f releases.pom.xml \
    -s ./settings.xml \
    -Pproduction \
    --quiet

COPY . .

RUN mvn clean package \
    -f releases.pom.xml \
    -DskipTests \
    -Pproduction \
    -Ptransformers-${INTAKE} \
    -Dcheckstyle.skip \
    --quiet

FROM openjdk:11-jre-slim

ARG INTAKE

WORKDIR /usr/app

COPY --from=build /usr/app/target/transformers-${INTAKE}-*.jar intake.jar

CMD ["java", "-jar", "/usr/app/intake.jar"]
