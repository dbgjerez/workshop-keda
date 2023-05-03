package main

import (
	"stock-api/interfaces"
	"stock-api/services"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

const (
	SERVER_PORT string = ":8081"
)

func main() {
	router := gin.Default()
	router.SetTrustedProxies(nil)
	router.Use(cors.Default())

	sService := services.NewStockService()

	v1 := router.Group("/api/v1")
	{
		h := interfaces.NewHealthcheckHandler()
		v1.GET("/health", h.HealthcheckGetHandler())

		s := interfaces.NewInfoHandler()
		v1.GET("/info", s.InfoGetHandler())

		stockHandler := interfaces.NewStockHandler(sService)
		v1.POST("/stock", stockHandler.CreateStock())
	}

	router.Run(SERVER_PORT)
}
