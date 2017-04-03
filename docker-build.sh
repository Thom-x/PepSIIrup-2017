docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
cd com.SII.RegisterService
docker build -t eureka .
cd ../com.SII.PersonneService
docker build -t person .
cd ../com.SII.EventService
docker build -t event .
cd ../com.SII.ClientService
docker build -t client .
docker run -p 1111:1111 -d eureka
docker run -p 2222:2222 -d person
docker run -p 3333:3333 -d event
docker run -p 80:80 -d client