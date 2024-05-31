
#!/bin/bash

echo " Starting Portal"
sudo docker compose up -d laof-liferay
sudo docker compose logs --follow laof-liferay
