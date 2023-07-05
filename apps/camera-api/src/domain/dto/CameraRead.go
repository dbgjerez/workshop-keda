package dto

import "time"

type CameraRead struct {
	Plate string    `json:"plate"`
	Type  string    `json:"type"`
	Date  time.Time `json:"date"`
}
