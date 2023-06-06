echo "Removing old objects"

podman stop mariadb
podman stop book-crud
podman rm mariadb
podman rm book-crud

podman network rm keda
podman network create keda

echo "Provisioning MariaDB"

podman run \
    -d  \
    --name mariadb  \
    --env MARIADB_USER=book \
    --env MARIADB_PASSWORD=book123 \
    --env MARIADB_ROOT_PASSWORD=book123 \
    --env MARIADB_DATABASE=book \
    --network keda \
    -p 3306:3306 \
    mariadb:latest

sleep 10
echo "Provisioning book-crud"

podman run \
    -d \
    --name book-crud \
    --env QUARKUS_DATASOURCE_JDBC_URL=jdbc:mariadb://mariadb:3306/book \
    --network keda \
    -p 8100:8080 \
    quay.io/dborrego/book-crud:0.2 

podman ps