# Projet Spring Web / Api Web
## Sujet
Créer une application de plateformes de discussion.
* En tant qu'utilisateur je veux pouvoir poster des discussions sur des sujets divers, les catégoriser

* En tant qu'utilisateur je veux pouvoir répondre et participer à une discussion, je veux pouvoir les consulter et les trier


* En tant qu'administrateur je veux pouvoir créer des catégories à disposition des utilisateurs

* En tant qu'administrateur je veux pourvoir gérer les discussions (bloquer, effacer, modérer)
## Déroulé
## Structure
Les Controllers sont séparés: les fichiers utilisés pour le côté API (@RestController) sont dans le dossier "api", les contrôleurs web (@Controller) sont dans le dossier "web".
### MVC
### Entités
### mysql
Lancer le container docker à l'aide de la commande suivante : 

docker run --name mysqll -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=hellospring -p 3306:3306 -d mysql:8.1

se connecter avec mysql:
docker exec -it mysqll mysql -u root -p

### Tests

## Sécurité
SpringSecurity a été utilisé pour gérer la connexion avec les rôles. exemple de rôle:
.authorizeHttpRequests(auth -> auth

            // USER
            .requestMatchers("/user/**").hasRole("USER")

            // ADMIN
            .requestMatchers("/admin/**").hasRole("ADMIN")
