package services

import (
	"context"
	"crypto/tls"
	"crypto/x509"
	"encoding/json"
	"io/ioutil"
	"log"
	"stock-api/domain/dto"
	"stock-api/utils"

	"github.com/segmentio/kafka-go"
)

const (
	kafkaBroker  = "KAFKA_BROKER"
	kafkaTopic   = "KAFKA_STOCK_TOPIC"
	clientCerPem = "CLIENT_CER_PEM_FILE"
	clientKeyPem = "CLIENT_KEY_PEM_FILE"
	serverCerPem = "SERVER_CER_PEM_FILE"
)

type StockService struct {
	w *kafka.Writer
}

func NewStockService() *StockService {
	kafkaBroker := utils.GetEnv(kafkaBroker, "localhost:9092")
	stockTopic := utils.GetEnv(kafkaTopic, "stock")
	clientCerFile := utils.GetEnv(clientCerPem, "client.cer.pem")
	clientKeyFile := utils.GetEnv(clientKeyPem, "client.key.pem")
	serverCerFile := utils.GetEnv(serverCerPem, "server.cer.pem")
	tlsConfig, _ := NewTLSConfig(clientCerFile, clientKeyFile, serverCerFile)
	tlsConfig.InsecureSkipVerify = true

	w := &kafka.Writer{
		Addr:                   kafka.TCP(kafkaBroker),
		Topic:                  stockTopic,
		Balancer:               &kafka.LeastBytes{},
		AllowAutoTopicCreation: true,
		Async:                  true,
		Transport: &kafka.Transport{
			TLS: tlsConfig,
		},
	}
	return &StockService{w: w}
}

func NewTLSConfig(clientCertFile, clientKeyFile, caCertFile string) (*tls.Config, error) {
	tlsConfig := tls.Config{}

	// Load client cert
	cert, err := tls.LoadX509KeyPair(clientCertFile, clientKeyFile)
	if err != nil {
		return &tlsConfig, err
	}
	tlsConfig.Certificates = []tls.Certificate{cert}

	// Load CA cert
	caCert, err := ioutil.ReadFile(caCertFile)
	if err != nil {
		return &tlsConfig, err
	}
	caCertPool := x509.NewCertPool()
	caCertPool.AppendCertsFromPEM(caCert)
	tlsConfig.RootCAs = caCertPool

	tlsConfig.BuildNameToCertificate()
	return &tlsConfig, err
}

func (service *StockService) CreateStock(stock dto.Stock) error {
	msg, _ := json.Marshal((stock))
	message := kafka.Message{Value: msg}
	err := service.w.WriteMessages(context.Background(), message)
	if err != nil {
		log.Printf("%v", err)
		return err
	}
	return nil
}
