package services

import (
	"context"
	"encoding/json"
	"log"
	"stock-api/domain/dto"
	"stock-api/utils"

	"github.com/segmentio/kafka-go"
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
	w := &kafka.Writer{
		Addr:                   kafka.TCP(kafkaBroker),
		Topic:                  stockTopic,
		Balancer:               &kafka.LeastBytes{},
		AllowAutoTopicCreation: true,
		Async:                  true,
	}
	return &StockService{w: w}
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
