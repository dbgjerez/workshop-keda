package dto

type Stock struct {
	Id       string  `json:"id"`
	Type     string  `json:"type"`
	Quantity int     `json:"quantity"`
	Price    float32 `json:"price"`
}
