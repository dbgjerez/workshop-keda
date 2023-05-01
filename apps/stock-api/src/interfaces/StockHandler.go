package interfaces

import (
	"net/http"
	"stock-api/domain/dto"
	"stock-api/services"

	"github.com/gin-gonic/gin"
)

const (
	ParamErrorMsg = "errorMsg" // error
	ParamDataName = "data"     // data name
)

type StockHandler struct {
	sService *services.StockService
}

func NewStockHandler(sService *services.StockService) *StockHandler {
	return &StockHandler{
		sService: sService,
	}
}

func (handler *StockHandler) CreateStock() func(c *gin.Context) {
	return func(c *gin.Context) {
		var stock dto.Stock
		if c.BindJSON(&stock) != nil {
			c.JSON(http.StatusBadRequest, gin.H{ParamErrorMsg: "Bad request!"})
			c.Abort()
			return
		}
		err := handler.sService.CreateStock(stock)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{ParamErrorMsg: err.Error()})
			c.Abort()
			return
		}
		c.JSON(http.StatusCreated, gin.H{ParamDataName: stock})
	}
}
