echo "Provisioning MariaDB"

podman stop mariadb
podman rm mariadb

podman run \
    -d  \
    --name mariadb  \
    --env MARIADB_USER=book \
    --env MARIADB_PASSWORD=book123 \
    --env MARIADB_ROOT_PASSWORD=book123 \
    --env MARIADB_DATABASE=book \
    -p 3306:3306 \
    mariadb:latest

podman ps