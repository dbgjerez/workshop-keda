import React from 'react'
import config from "../config/app.parking.config.json"
import ParkingCRUD from '../components/crud/ParkingCRUD';

const Parking = () => {
    return <ParkingCRUD config={config}/> 
};

export default Parking;