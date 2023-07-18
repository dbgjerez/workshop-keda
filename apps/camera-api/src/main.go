package main

import (
	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/interfaces"
	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/services"
	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/utils"
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

const (
	serverDefaultPort = "8080"
	serverPort        = "SERVER_PORT"
)

func main() {
	router := gin.Default()
	router.SetTrustedProxies(nil)
	router.Use(cors.Default())

	sService := services.NewCameraReadService()

	v1 := router.Group("/api/v1")
	{
		h := interfaces.NewHealthcheckHandler()
		v1.GET("/health", h.HealthcheckGetHandler())

		s := interfaces.NewInfoHandler()
		v1.GET("/info", s.InfoGetHandler())

		cameraHandler := interfaces.NewCameraReadHandler(sService)
		v1.POST("/camera/read", cameraHandler.CreateCameraRead())
	}
	port := utils.GetEnv(serverPort, serverDefaultPort)
	router.Run(":" + port)
}
