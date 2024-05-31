
#!/bin/bash

echo " Starting DB"
sudo docker compose up -d laof-bdd
sudo docker compose logs --follow laof-bdd
