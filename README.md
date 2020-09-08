##Quickstart
Task 1. Run Ingestion - port 8080       
Task 2. Run Streaming    
Task 3. Run Query



Task 3.Bonus Run Streaming Docker

## Docker
`docker pull sequenceiq/hadoop-docker:2.7.0`    
`docker run -it sequenceiq/hadoop-docker:2.7.0 /etc/bootstrap.sh -bash`       
`docker run -it -d -p 50010:50010 -p 50020:50020 -p 8570:50070 -p 8575:50075 -p 8590:50090 -p 9000:9000 sequenceiq/hadoop-docker:2.7.0 /etc/bootstrap.sh -bash`   
docker container cli  by default it does not give rights (and avoid to generate new user)   
`hdfs dfs -chmod 777 /user`   




 


## Ubuntu
docker run -it ubuntu bash  
docker run --rm -ti -p 1234:1234 -p 4321:4321 --name echo-server ubuntu bash  
apt-get update && apt-get install -y netcat    



