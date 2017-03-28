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
docker run -p 1111:1111 -d eureka eureka
docker run -p 2222:2222 -d person person
docker run -p 3333:3333 -d event event
docker run -p 4444:4444 -d client client