package interfaces

import (
	"billing-api/services"
	"net/http"

	"github.com/gin-gonic/gin"
)

const (
	ParamErrorMsg = "errorMsg" // error
	ParamDataName = "data"     // data name
)

type BillingHandler struct {
	sService *services.BillingService
}

func NewBillingHandler(sService *services.BillingService) *BillingHandler {
	return &BillingHandler{
		sService: sService,
	}
}

func (handler *BillingHandler) CreateBilling() func(c *gin.Context) {
	return func(c *gin.Context) {
		c.JSON(http.StatusNotImplemented, gin.H{ParamDataName: nil})
	}
}
