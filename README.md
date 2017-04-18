# PepSIIrup-2017
Application to create and manage SII Nantes events

# Prerequisites
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/) (optionnal)
* [RabbitMq](https://www.rabbitmq.com/)
* [Seq/Serilog](https://getseq.net/) (optionnal)

# Installation

Clone this repository and then build every micro-services using Maven
You will have to change ip address in the applications.properties files to fit to your server
You also need to install RabbitMq (default port 5672) and Seq (default port 5341)
```bash
git clone https://github.com/TraineeSIIp/PepSIIrup-2017.git
cd PepSIIrup-2017/
mvn install:install-file -Dfile=lib/sqljdbc42.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc42 -Dversion=4.2 -Dpackaging=jar
mvn install:install-file -Dfile=lib/serilogj.jar -DgroupId=serilogj -DartifactId=serilogj -Dversion=0.3 -Dpackaging=jar
//for each micro services
mvn package
```
# Deployment

Run the following script to start the app
```bash
# build docker images and start them
./docker-build.sh
```
# Usage
The Rest client is up on port 80, and you can find others services at port 1111, port 2222 and port 3333

# License
[MIT License](https://github.com/TraineeSIIp/PepSIIrup-2017/blob/master/LICENSE)

# Authors
Dorian Coqueron, Pierre Gaultier
