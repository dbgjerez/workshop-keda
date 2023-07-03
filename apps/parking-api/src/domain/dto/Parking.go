package dto

type Parking struct {
	IdParking string       `json:"idParking"`
	Plate     string       `json:"plate"`
	Type      string       `json:"type"`
	Dates     ParkingDates `json:"dates"`
}

type ParkingDates struct {
	InDate  int32 `json:"inDate"`
	OutDate int32 `json:"outDate"`
}
