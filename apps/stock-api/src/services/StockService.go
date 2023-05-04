package services

import (
	"context"
	"crypto"
	"crypto/tls"
	"crypto/x509"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"stock-api/domain/dto"
	"stock-api/utils"

	"github.com/segmentio/kafka-go"
	"software.sslmate.com/src/go-pkcs12"
)

const (
	kafkaBroker = "KAFKA_BROKER"
	kafkaTopic  = "KAFKA_STOCK_TOPIC"
)

type StockService struct {
	w *kafka.Writer
}

func NewStockService() *StockService {
	kafkaBroker := utils.GetEnv(kafkaBroker, "localhost:9092")
	stockTopic := utils.GetEnv(kafkaTopic, "stock")
	tlsConfig, err := tlsCfg("ca.p12", "VFJWZTUwelVBR0x6", "user.p12", keyStorePassword)
	if err != nil {
		log.Fatal(err)
	}
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

func tlsCfg(trustStoreFile string, trustStorePassword string, keyStoreFile string, keyStorePassword string) (*tls.Config, error) {
	trustStore, err := ioutil.ReadFile(trustStoreFile)
	if err != nil {
		return nil, fmt.Errorf("49: %w", err)
	}

	trustStoreCerts, err := pkcs12.DecodeTrustStore(trustStore, trustStorePassword)
	if err != nil {
		return nil, fmt.Errorf("54: %w", err)
	}

	certPool, err := x509.SystemCertPool()
	if err != nil {
		return nil, fmt.Errorf("59: %w", err)
	}

	for _, cert := range trustStoreCerts {
		certPool.AddCert(cert)
	}

	keyStore, err := ioutil.ReadFile(keyStoreFile)
	if err != nil {
		return nil, fmt.Errorf("68: %w", err)
	}

	keyStoreKey, keyStoreCert, err := pkcs12.Decode(keyStore, keyStorePassword)
	if err != nil {
		return nil, fmt.Errorf("72: %w", err)
	}

	clientCert := tls.Certificate{
		Certificate: [][]byte{keyStoreCert.Raw},
		PrivateKey:  keyStoreKey.(crypto.PrivateKey),
		Leaf:        keyStoreCert,
	}

	return &tls.Config{
		InsecureSkipVerify: false,
		MaxVersion:         tls.VersionTLS12,
		Certificates:       []tls.Certificate{clientCert},
		RootCAs:            certPool,
	}, nil
}

func (service *StockService) CreateStock(stock dto.Stock) error {
	msg, _ := json.Marshal((stock))
	message := kafka.Message{Value: msg}
	err := service.w.WriteMessages(context.TODO(), message)
	if err != nil {
		log.Printf("%v", err)
		return err
	}
	return nil
}
