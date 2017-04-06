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