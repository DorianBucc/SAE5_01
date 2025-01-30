# Dockerfile :

Commande pour le build : `docker build -t api_sae .`

Commande pour le lancer manuellement : `docker run -d -p 3000:3000 api_sae`

# DockerCompose :

Commande pour lancer : `docker-compose up -d` + `--build` si besoin pour build l'image à chaque fois.

Commande pour stopper : `docker-compose down` + `-v` pour supprimer.
