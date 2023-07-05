oc get secret -n kafka kafka-cluster-ca-cert -o json | jq -r '.data["ca.p12"]' | base64 -d > ca.p12
oc get secret -n kafka kafka-consumer-parking-operation -o json | jq -r '.data["user.p12"]' | base64 -d > user.p12

oc create secret generic -n dev-backend keda-kafka-connection \
    --from-file=ca.p12=ca.p12 \
    --from-file=user.p12=user.p12 \
    --from-literal=ca.password=$(oc get secret -n kafka kafka-cluster-ca-cert -o json | jq -r '.data["ca.password"]' | base64 -d) \
    --from-literal=user.password=$(oc get secret -n kafka kafka-consumer-parking-operation -o json | jq -r '.data["user.password"]' | base64 -d) \
    --from-literal=user.name=$(oc get kafkauser -n kafka kafka-consumer-parking-operation -o json | jq -r '.metadata.name') \
    --from-literal=tls=enable

rm ca.p12
rm user.p12