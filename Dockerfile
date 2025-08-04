# ----------- Stage 1 : build ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# Copia arquivos de build primeiro para cache
COPY pom.xml .
RUN mvn -q dependency:go-offline
# Copia o restante da aplicação
COPY src ./src
# Compila a aplicação (gera o .jar)
RUN mvn -q clean package -DskipTests

# ----------- Stage 2 : runtime ---------
FROM eclipse-temurin:21-jdk-alpine

# Configurações de JVM para melhor performance
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

WORKDIR /app

# Argumento para definir o JAR final
ARG JAR_FILE=/app/target/*.jar

# Copia o jar gerado para a imagem final
COPY --from=build /app/target/*.jar /app/app.jar

# Exponha a porta usada pela aplicação Spring Boot e Actuator
EXPOSE 8080

# Adiciona healthcheck para monitoramento
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Ativa a substituição de config através de envs (exemplo) e executa
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]