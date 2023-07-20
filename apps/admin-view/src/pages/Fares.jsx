import React from 'react'
import config from "../config/app.fare.config.json"
import FareCRUD from '../components/crud/FareCRUD';

const Fare = () => {
    return <FareCRUD config={config}/> 
};

export default Fare;