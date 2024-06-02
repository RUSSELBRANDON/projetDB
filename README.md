# gestion des emploi de temps (Backend)

Ce projet a pour objectif dautomatiser le processus de gestion des emplois de temps dans un etablissement.

## Table des Matières

- [Pré-requis](#pré-requis)
- [Installation](#installation)
- [Démarrage](#démarrage)
- [Utilisation](#utilisation)
- [API Endpoints](#api-endpoints)
- [Tests](#tests)
- [Contribution](#contribution)
- [License](#license)

## Pré-requis

Pour exécuter ce projet, vous aurez besoin de :

- Java 22 ou version supérieure
- Maven 3.6.3 ou version supérieure
- Une base de données  PostgreSQL

## Installation

1. Clonez le dépôt sur votre machine locale :

    ```bash
    git clone https://github.com/votre-utilisateur/nom-du-projet.git
    cd nom-du-projet
    ```

2. Configurez la base de données :

    - Créez une base de données `timetables`
    - Mettez à jour les informations de connexion dans le fichier `src/main/resources/application.properties` :

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/timetables
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

3. Compilez le projet avec Maven :

    ```bash
    mvn clean install
    ```

## Démarrage

Pour démarrer l'application, exécutez la commande suivante :

```bash
mvn spring-boot:run
```
