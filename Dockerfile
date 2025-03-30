# Estágio de construção (builder)
FROM eclipse-temurin:21-jdk-alpine AS builder

# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto para dentro do contêiner
COPY . .

# Rodar o Maven para construir o arquivo JAR
RUN ./mvnw clean package -DskipTests

# Definir a variável JAR_FILE para localizar o arquivo JAR gerado
# ARG JAR_FILE=target/*.jar

# Estágio final
FROM eclipse-temurin:21-jdk-alpine

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# RUN ls -l /app/target

# Copiar o JAR gerado do estágio builder para o contêiner
# COPY --from=builder target/*.jar app.jar
# COPY --from=builder target/spring_pjc_2025.jar app.jar
# COPY --from=builder /target/spring_pjc_2025-0.0.1-SNAPSHOT.jar app.jar
 COPY --from=builder /app/target/spring_pjc_2025-0.0.1-SNAPSHOT.jar app.jar


# Expor a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


# Etapa 1: Build da aplicação
# FROM eclipse-temurin:21-jdk-alpine AS builder

# WORKDIR /app

# Copia o projeto para dentro do contêiner
# COPY . . 

# Compila o projeto e gera o JAR
# RUN ./mvnw clean package -DskipTests

# Etapa 2: Criando a imagem final apenas com o JAR
# FROM eclipse-temurin:21-jdk-alpine

# WORKDIR /app

# Define o nome do JAR gerado na primeira etapa
# ARG JAR_FILE=target/*.jar

# Copia o JAR para a imagem final
# COPY --from=builder ${JAR_FILE} app.jar

# Expõe a porta 8080
# EXPOSE 8080

# Comando de inicialização
# ENTRYPOINT ["java", "-jar", "app.jar"]
