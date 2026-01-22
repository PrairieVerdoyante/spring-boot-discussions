# Projet Spring Web / Api Web
## Sujet
Créer une application de plateformes de discussion.
Voici les directives générales :

* En tant qu'utilisateur je veux pouvoir poster des discussions sur des sujets divers, les catégoriser

* En tant qu'utilisateur je veux pouvoir répondre et participer à une discussion, je veux pouvoir les consulter et les trier


* En tant qu'administrateur je veux pouvoir créer des catégories à disposition des utilisateurs

* En tant qu'administrateur je veux pouvoir gérer les discussions (bloquer, effacer, modérer)
## Déroulé
### Architecture
Le projet a été commencé en suivant les exercices du cours, notamment le chapitre 2 (API Rest):
* Création d'une ressource (discussion)
* Création d'un contrôleur (DiscussionController)
    * interface et implémentation
* Création d'un service (DiscussionService)
* Céation d'un repository.

Le repository est injecté automatiquement dans le service.

### Spring Security
Dans le projet, il y a deux rôles principaux : **administrateur** et **utilisateur**. J'ai voulu ajouter SpringSecurity afin de pouvoir définir des pages réservées à chacun de ces rôles.
#### Création d'un compte
Il est possible de créer un compte. A la création d'un compte, l'utilisateur est créé et ajouté dans la table app_user (UserEntity, qui implémente UserDetails de SpringSecurity et gère ainsi les utilisateurs).
![](src/img/creation_compte_admin.png)
#### Connexion
Une fois l'utilisateur connecté, un cookie de session JSESSIONID est généré, ce qui nous permettra d'utiliser les routes sans renseigner de champ de connexion.
![connexion](src/img/connexion_admin.png)

Voici un exemple de définition de routes en fonction de chaque rôle :

            // USER
            .requestMatchers("/user/**").hasRole("USER")

            // ADMIN
            .requestMatchers("/admin/**").hasRole("ADMIN")

### Thymeleaf
## Structure
Les Controllers sont séparés: les fichiers utilisés pour le côté API (@RestController) sont dans le dossier "api", les contrôleurs web (@Controller) sont dans le dossier "web".

### Entités
### mysql
Il est possible d'utiliser mysql pour ce projet. h2 a été utilisé durant le développement pour visualiser plus facilement les bases de données via l'interface web.
Voici les modifications à effectuer.

Dans le fichier `application.properties`, remplacer "h2" par "mysql".

![usesql](src/img/use_mysql.png)

Lancer le container docker à l'aide de la commande suivante : 

`docker run --name mysqll -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=hellospring -p 3306:3306 -d mysql:8.1`

Se connecter avec mysql:
`docker exec -it mysqll mysql -u root -p`

### Tests
