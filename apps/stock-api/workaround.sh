oc get secret -n kafka kafka-cluster-ca-cert -o json | jq -r '.data["ca.p12"]' | base64 -d > ca.p12
oc get secret -n kafka kafka-producer-stock-topic -o json | jq -r '.data["user.p12"]' | base64 -d > user.p12

openssl pkcs12 -in ca.p12 -password pass:$(oc get secret -n kafka kafka-cluster-ca-cert -o json | jq -r '.data["ca.password"]' | base64 -d) -nokeys -out server.cer.pem
openssl pkcs12 -in user.p12 -password pass:$(oc get secret -n kafka kafka-producer-stock-topic -o json | jq -r '.data["user.password"]' | base64 -d) -nokeys -out client.cer.pem
openssl pkcs12 -in user.p12 -password pass:$(oc get secret -n kafka kafka-producer-stock-topic -o json | jq -r '.data["user.password"]' | base64 -d) -nodes -nocerts -out client.key.pem

oc create secret generic kafka-connection \
 --from-file=server.cer.pem=server.cer.pem \
 --from-file=client.cer.pem=client.cer.pem \
 --from-file=client.key.pem=client.key.pem