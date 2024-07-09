
#!/bin/bash

echo " Starting environment"
sudo docker compose up -d
sudo docker compose logs --follow laof-liferay  laof-flowise laof-n8n
