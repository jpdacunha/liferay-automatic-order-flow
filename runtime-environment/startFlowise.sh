
#!/bin/bash

echo " Starting Flowise ..."
sudo docker compose up -d laof-flowise
sudo docker compose logs --follow laof-flowise
