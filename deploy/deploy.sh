docker network create airbnb-network
docker-compose -f ./docker-compose.node_01.yml up --build -d
docker-compose -f ./docker-compose.node_02.yml up --build -d
docker-compose -f ./docker-compose.node_03.yml up --build -d