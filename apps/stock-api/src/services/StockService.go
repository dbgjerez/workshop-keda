package services

import (
	"context"
	"encoding/json"
	"log"
	"stock-api/domain/dto"

	"github.com/segmentio/kafka-go"
)

const (
	kafkaTopic  = "KAFKA_STOCK_TOPIC"
	kafkaBroker = "KAFKA_BROKER"
)

type StockService struct {
	w *kafka.Writer
}

func NewStockService() *StockService {
	w := &kafka.Writer{
		Addr:                   kafka.TCP("localhost:9092"),
		Topic:                  "stock",
		Balancer:               &kafka.LeastBytes{},
		AllowAutoTopicCreation: true,
	}
	return &StockService{w: w}
}

func (service *StockService) CreateStock(stock dto.Stock) error {
	msg, _ := json.Marshal((stock))
	messages := []kafka.Message{{Value: msg}}
	err := service.w.WriteMessages(context.TODO(), messages...)
	if err != nil {
		log.Printf("%v", err)
		return err
	}
	return nil
}
