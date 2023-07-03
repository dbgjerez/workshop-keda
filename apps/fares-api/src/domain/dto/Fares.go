package dto

type Fare struct {
	Type   string `json:"type"`
	Prices string `json:"prices"`
}

type Prices struct {
	Minute float32 `json:"min"`
}
