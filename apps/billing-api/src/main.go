package main

import (
	"billing-api/interfaces"
	"billing-api/services"

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

	sService := services.NewBillingService()

	v1 := router.Group("/api/v1")
	{
		h := interfaces.NewHealthcheckHandler()
		v1.GET("/health", h.HealthcheckGetHandler())

		s := interfaces.NewInfoHandler()
		v1.GET("/info", s.InfoGetHandler())

		billingHandler := interfaces.NewBillingHandler(sService)
		v1.POST("/parking", billingHandler.CreateBilling())
	}

	router.Run(SERVER_PORT)
}
