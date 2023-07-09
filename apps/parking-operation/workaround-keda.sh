
oc create secret generic -n dev-backend keda-kafka-connection \
    --from-literal=username=kafka-consumer-keda \
    --from-literal=password=--- \
    --from-literal=sasl=scram_sha512
