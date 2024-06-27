
#!/bin/bash

echo " Starting Flowise ..."
sudo docker compose up -d laof-flowise

echo " Starting n8n ..."
sudo docker compose up -d laof-n8n

echo " Starting Portal"
sudo docker compose up -d laof-liferay


sudo docker compose logs --follow laof-flowise laof-liferay laof-n8n
