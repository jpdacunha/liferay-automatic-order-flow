
#!/bin/bash
echo " Stopping Flowise..."
sudo docker compose down laof-flowise

echo " Stopping n8n..."
sudo docker compose down laof-n8n

echo " Stopping Portal..."
sudo docker compose down laof-liferay
