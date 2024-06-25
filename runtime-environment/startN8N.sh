
#!/bin/bash

echo " Starting n8n ..."
sudo docker compose up -d laof-n8n
sudo docker compose logs --follow laof-n8n
