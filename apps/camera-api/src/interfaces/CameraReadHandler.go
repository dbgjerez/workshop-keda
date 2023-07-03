package interfaces

import (
	"log"
	"net/http"

	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/domain/dto"
	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/services"
	"github.com/gin-gonic/gin"
)

const (
	ParamErrorMsg = "errorMsg" // error
	ParamDataName = "data"     // data name
)

type CameraReadHandler struct {
	sService *services.CameraReadService
}

func NewCameraReadHandler(sService *services.CameraReadService) *CameraReadHandler {
	return &CameraReadHandler{
		sService: sService,
	}
}

func (handler *CameraReadHandler) CreateCameraRead() func(c *gin.Context) {
	return func(c *gin.Context) {
		var read dto.CameraRead
		if c.BindJSON(&read) != nil {
			c.JSON(http.StatusBadRequest, gin.H{ParamErrorMsg: "Bad request!"})
			c.Abort()
			return
		}
		log.Printf("Received new read: %v", read)
		err := handler.sService.CreateCameraRead(read)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{ParamErrorMsg: err.Error()})
			c.Abort()
			return
		}
		c.JSON(http.StatusCreated, gin.H{ParamDataName: read})
	}
}
