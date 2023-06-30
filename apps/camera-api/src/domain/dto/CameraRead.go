package dto

type CameraRead struct {
	Plate string  `json:"plate"`
	Type  string  `json:"type"`
	Date  float32 `json:"date"`
}
