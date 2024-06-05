# Utilisez une image de base OpenJDK légère
FROM openjdk:8-jre-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR dans l'image Docker
COPY target/gestion_users.jar gestion_users.jar

# Exposer le port sur lequel l'application s'exécute
EXPOSE 8301

# Ajouter un utilisateur non root pour des raisons de sécurité
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
USER appuser

# Définir le point d'entrée pour exécuter le fichier JAR
ENTRYPOINT ["java", "-jar", "gestion_users.jar"]

# Ajouter une instruction HEALTHCHECK pour vérifier l'état de l'application
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8301/actuator/health || exit 1
