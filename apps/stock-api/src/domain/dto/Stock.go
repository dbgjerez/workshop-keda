package dto

type Stock struct {
	Type     string `json:"type"`
	Quantity int    `json:"quantity"`
	Id       int    `json:"id"`
	Title    string `json:"title"`
}
