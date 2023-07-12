package services

import (
	"context"
	"crypto/tls"
	"crypto/x509"
	"encoding/json"
	"io/ioutil"
	"log"

	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/domain/dto"
	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/utils"
	"github.com/segmentio/kafka-go"
)

const (
	kafkaBroker            = "KAFKA_BROKER"
	kafkaTopic             = "KAFKA_CAMERA_NEW_PICTURE_TOPIC"
	defaultValueKafkaTopic = "camera-new-picture"
	clientCerPem           = "CLIENT_CER_PEM_FILE"
	clientKeyPem           = "CLIENT_KEY_PEM_FILE"
	serverCerPem           = "SERVER_CER_PEM_FILE"
	enableTLS              = "KAFKA_ENABLE_TLS"
	enableTLSDefault       = "false"
)

type TLSMetadata struct {
	enableTLS     bool
	clientCerFile string
	clientKeyFile string
	serverCerFile string
}

type CameraReadService struct {
	w *kafka.Writer
}

func LoadTLSMetadata() TLSMetadata {
	metadata := TLSMetadata{}
	metadata.clientCerFile = utils.GetEnv(clientCerPem, "client.cer.pem")
	metadata.clientKeyFile = utils.GetEnv(clientKeyPem, "client.key.pem")
	metadata.serverCerFile = utils.GetEnv(serverCerPem, "server.cer.pem")
	metadaEnableTLS := utils.GetEnv(enableTLS, enableTLSDefault)
	if metadaEnableTLS == "true" || metadaEnableTLS == "True" || metadaEnableTLS == "TRUE" {
		metadata.enableTLS = true
	}
	return metadata
}

func NewCameraReadService() *CameraReadService {
	kafkaBroker := utils.GetEnv(kafkaBroker, "localhost:9092")
	cameraTopic := utils.GetEnv(kafkaTopic, defaultValueKafkaTopic)
	metadaTLS := LoadTLSMetadata()
	write := kafka.Writer{
		Addr:     kafka.TCP(kafkaBroker),
		Topic:    cameraTopic,
		Balancer: &kafka.LeastBytes{},
		Async:    true,
	}
	if metadaTLS.enableTLS {
		tlsConfig, _ := NewTLSConfig(metadaTLS)
		write.Transport = &kafka.Transport{
			TLS: tlsConfig,
		}
	}
	return &CameraReadService{w: &write}
}

func NewTLSConfig(metada TLSMetadata) (*tls.Config, error) {
	tlsConfig := tls.Config{}
	if metada.enableTLS {
		// Load client cert
		cert, err := tls.LoadX509KeyPair(metada.clientCerFile, metada.clientCerFile)
		if err != nil {
			return &tlsConfig, err
		}
		tlsConfig.Certificates = []tls.Certificate{cert}

		// Load CA cert
		caCert, err := ioutil.ReadFile(metada.serverCerFile)
		if err != nil {
			return &tlsConfig, err
		}
		caCertPool := x509.NewCertPool()
		caCertPool.AppendCertsFromPEM(caCert)
		tlsConfig.RootCAs = caCertPool

		tlsConfig.BuildNameToCertificate()
		tlsConfig.InsecureSkipVerify = true
	}
	return &tlsConfig, nil
}

func (service *CameraReadService) CreateCameraRead(cameraRead dto.CameraRead) error {
	msg, _ := json.Marshal((cameraRead))
	message := kafka.Message{Value: msg}
	err := service.w.WriteMessages(context.Background(), message)
	if err != nil {
		log.Printf("Error trying to send data to Kafka: %v", err)
		return err
	}
	return nil
}
