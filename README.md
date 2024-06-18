# Automatic Order Flow
This project automates order processing by leveraging Liferay for content management and an AI system for email/text interpretation.
Customers place orders via email, which the AI reads and understands to extract the order details.
This workflow illustrates the steps to automate the processing of a product order request using Flowise. It shows how to receive and analyze an email/text, use an AI service to interpret the order, and then process and validate the order within a system.

# Setup to launch the project

## Build
    
        $ cd ./runtime-environment
        $ ./build.sh
        
## Deploy
    
        $ cd ./liferay-workspace
        $ ./gradlew deploy
        $ cd ../runtime-environment
        $ ./deploy.sh
        
## Start project   
(you should respect order to launch project without problem)
    
        $ cd ./runtime-environment
        $ ./startDb.sh
        $ ./startPortal.sh

if you are login for the first time: tap "test" as name and password then you should change it.
        
## Stop project 
(you should respect order to launch project without problem)
    
        $ cd ./runtime-environment
        $ ./stopPortal.sh
        $ ./stopDb.sh

# Utils

## Portals

| Service | title | links |credentials | 
|----------|:-------------:|------:|------:|
| Liferay| http://localhost:8080/ | URL of Liferay portal|test/test|
| Flowise| http://localhost:3000/ | URL of Flowise|inetumuser/inetumpass|


## TODO
### Step 01 : Ameliorations
- Indenter le code JS 
- Gérer l'erreur en cas de création de la commande si possible avec un if/else
- Ajouter des commentaires
- Renommer le flow : Automatic Order Flow
- Mettre des noms parlants sur les étapes.

### Step 02 : Low Code
 - Ajouter une action au lieu du model listener / supprimer le code JAVA 
 - Voir pour gérer les status en utilisant une picklist (s'inspirer de l'exemple de la DevCon) => Ne pas utiliser l'état system
 - 






















        
