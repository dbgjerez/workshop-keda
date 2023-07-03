package main

import (
	"fares-api/interfaces"
	"fares-api/services"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

const (
	SERVER_PORT string = ":8080"
)

func main() {
	router := gin.Default()
	router.SetTrustedProxies(nil)
	router.Use(cors.Default())

	sService := services.NewFaresService()

	v1 := router.Group("/api/v1")
	{
		h := interfaces.NewHealthcheckHandler()
		v1.GET("/health", h.HealthcheckGetHandler())

		s := interfaces.NewInfoHandler()
		v1.GET("/info", s.InfoGetHandler())

		parkingHandler := interfaces.NewFaresHandler(sService)
		v1.POST("/fares", parkingHandler.CreateFare())
	}

	router.Run(SERVER_PORT)
}
