package interfaces

import (
	"fares-api/services"
	"net/http"

	"github.com/gin-gonic/gin"
)

const (
	ParamErrorMsg = "errorMsg" // error
	ParamDataName = "data"     // data name
)

type FaresHandler struct {
	sService *services.FaresService
}

func NewFaresHandler(sService *services.FaresService) *FaresHandler {
	return &FaresHandler{
		sService: sService,
	}
}

func (handler *FaresHandler) CreateFare() func(c *gin.Context) {
	return func(c *gin.Context) {
		c.JSON(http.StatusNotImplemented, gin.H{ParamDataName: nil})
	}
}
