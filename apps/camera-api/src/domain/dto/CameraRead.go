package dto

type CameraRead struct {
	Plate string `json:"plate"`
	Type  string `json:"type"`
	Date  int32  `json:"date"`
}
