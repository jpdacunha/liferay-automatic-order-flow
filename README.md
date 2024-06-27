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
        $ ./startApps.sh

if you are login for the first time: tap "test" as name and password then you should change it.
        
## Stop project 
(you should respect order to launch project without problem)
    
        $ cd ./runtime-environment
        $ ./stopApps.sh
        $ ./stopDb.sh

# Utils

## Portals

| Service | title | links |credentials | 
|----------|:-------------:|------:|------:|
| Liferay| http://localhost:8080/ |URL of Liferay portal|test/test|
| Flowise| http://localhost:3000/ |URL of Flowise|inetumuser/inetumpass|
| Flowise| http://localhost:5678/ |URL of n8n|demo@inetum.com/Inetum2024|

# Use cases
| Scenario/System prompt| Links |
|----------|:-------------:|
| P| [System prompt](https://github.com/jpdacunha/liferay-automatic-order-flow/blob/main/SystemPrompt)|
| S1| [Nominal scenario](https://github.com/jpdacunha/liferay-automatic-order-flow/blob/main/S1)|
| S2| [One or more products are not available](https://github.com/jpdacunha/liferay-automatic-order-flow/blob/main/S2)|
| S3| [All products are not available](https://github.com/jpdacunha/liferay-automatic-order-flow/blob/main/S3)|
| S4| [Missing information](https://github.com/jpdacunha/liferay-automatic-order-flow/blob/main/S4)|
























        
