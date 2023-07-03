package interfaces

import (
	"net/http"
	"parking-api/services"

	"github.com/gin-gonic/gin"
)

const (
	ParamErrorMsg = "errorMsg" // error
	ParamDataName = "data"     // data name
)

type ParkingHandler struct {
	sService *services.ParkingService
}

func NewParkingHandler(sService *services.ParkingService) *ParkingHandler {
	return &ParkingHandler{
		sService: sService,
	}
}

func (handler *ParkingHandler) CreateCameraRead() func(c *gin.Context) {
	return func(c *gin.Context) {
		c.JSON(http.StatusNotImplemented, gin.H{ParamDataName: nil})
	}
}
