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
        
## Stop project 
(you should respect order to launch project without problem)
    
        $ cd ./runtime-environment
        $ ./stopPortal.sh
        $ ./stopDb.sh

You can now log in from http://localhost:8080/ to Liferay.
if you are login for the first time: tap "test" as name and password then you should change it.


# Utils

## Portals

| Service | title | links | 
|----------|:-------------:|------:|
| Liferay| http://localhost:8080/ | URL of Liferay portal|






















        
