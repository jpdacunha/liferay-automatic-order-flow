# General
This project automates order processing by leveraging Liferay for content management and an AI system for email/text interpretation.
Customers place orders via email, which the AI reads and understands to extract the order details.

# Automatic Order Flow
This workflow illustrates the steps to automate the processing of a product order request using Flowise/n8n. It shows how to receive and analyze an email/, use an AI service to interpret the order, and then process and validate the order within a system.

# Initial Setup

# Step 1: Create Objects in Liferay

    Create Order Contact Object: Define the necessary attributes and properties for the order contact within Liferay.
    Create Product Catalog Object: Set up the product catalog object, detailing its structure and elements.
    Automate Launch: Utilize a batch client-extension to automate the creation and management of these objects.

# Step 2: Create the Workflow

    Using Flow Wise or n8n: Develop the workflow using either Flow Wise or n8n to manage the processes and interactions between created objects.

By breaking down the steps, you ensure a clear and organized approach to setting up and automating your local environment on Liferay.

# Setup to launch the project

# Build
    
    $ cd ./runtime-environment
    $ ./build.sh
        
# Deploy
    
    $ cd ./liferay-workspace
    $ ./gradlew deploy
    $ cd ../runtime-environment
    $ ./deploy.sh
        
# Start project   
(you should respect order to launch project without problem)
    
    $ cd ./runtime-environment
    $ ./startDb.sh
    $ ./startPortal.sh
        
# Stop project 
(you should respect order to launch project without problem)
    
        $ cd ./runtime-environment
        $ ./stopPortal.sh
        $ ./stopDb.sh




























        
