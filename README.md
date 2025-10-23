# Wikimedia Recent Changes Stream (Kafka Demo)

<hr />

This project demonstrates a real-time data pipeline using Spring Boot + Apache Kafka + MySQL.
It listens to live Wikimedia edit events and stores them in a database.

<hr />

## Kafka Commands 


``
brew start kafka
``

``
brew start zookeeper
``


<hr />

## Docker 

``docker run --name wikimedia -p 3306:3306 -e MYSQL_DATABASE=wikimedia  -e MYSQL_ROOT_PASSWORD=Pavan@123 -d mysql ``
<hr />