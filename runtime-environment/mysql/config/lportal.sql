
CREATE DATABASE IF NOT EXISTS lportal74laof;

ALTER DATABASE lportal74laof CHARACTER SET utf8 COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'password';
flush privileges;