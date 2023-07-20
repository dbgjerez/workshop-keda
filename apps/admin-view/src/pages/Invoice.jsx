import React from 'react'
import config from "../config/app.invoice.config.json"
import InvoiceCRUD from '../components/crud/InvoiceCRUD';

const Invoice = () => {
    return <InvoiceCRUD config={config}/> 
};

export default Invoice;