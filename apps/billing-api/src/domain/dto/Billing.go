package dto

type Billing struct {
	IdBilling   string  `json:"idBilling"`
	Plate       string  `json:"plate"`
	Date        string  `json:"date"`
	PaymentType string  `json:"paymentType"`
	Amount      float32 `json:"amount"`
}
