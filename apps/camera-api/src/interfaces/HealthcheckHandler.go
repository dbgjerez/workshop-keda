package interfaces

import (
	"net/http"

	"github.com/dbgjerez/workshop-keda/apps/camera-api/src/domain/dto"
	"github.com/gin-gonic/gin"
)

type HealthcheckHandler struct {
}

func NewHealthcheckHandler() *HealthcheckHandler {
	return &HealthcheckHandler{}
}

func (handler *HealthcheckHandler) HealthcheckGetHandler() func(c *gin.Context) {
	return func(c *gin.Context) {
		h := dto.Health{}
		h.Status = dto.HealhStatusUp
		c.JSON(http.StatusOK, h)
	}
}
