# Projet de Microservices pour la Gestion des JO

## Membres du binôme
- Jean-Baptiste COLLIN
- Julien MARCILLAUD

## Description du Projet
Ce projet consiste en une application de gestion des événements sportifs des JO, construite en utilisant une architecture de microservices. Les principaux microservices sont :

- `Olympic_Service` : Microservice central pour la gestion des autres microservices.
- `Event_Scheduling_Service` : Gestion des événements.
- `Sites_Management_Service` : Gestion des sites.
- `Sports_Management_Service` : Gestion des sports.
- `User_Planning_Service` : Gestion des plannings des utilisateurs.

## Prérequis
- Java 8 ou version supérieure
- Docker et Docker Compose
- Maven

## Compilation et Exécution
### Étapes de compilation
1. Clonez le repository :
    ```sh
    git clone https://github.com/votre-repository.git
    cd votre-repository
    ```

2. Compilez le projet avec Maven :
    ```sh
    mvn clean install
    ```

### Exécution avec Docker Compose
1. Assurez-vous que Docker et Docker Compose sont installés sur votre machine.

2. Lancez les services :
    ```sh
    docker-compose up --build
    ```

3. Accédez à l'application via le navigateur à l'adresse suivante :
    ```
    http://localhost:8080
    ```

## Documentation Technique
### Choix Techniques
#### Frameworks
- **Spring Boot** : Développement des microservices.
- **Spring Data JPA** : Accès aux données.
- **Spring Cloud OpenFeign** : Communication entre microservices.

#### Base de Données
- **MySQL** : Stockage des données.
- **H2** : Base de données en mémoire pour les tests.

#### Conteneurisation
- **Docker** : Conteneurisation des microservices.
- **Docker Compose** : Orchestration des services.