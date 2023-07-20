import { useEffect, useState } from "react";
import { Form, Grid, Table } from 'semantic-ui-react'

const FareCRUD = (config) => {
    const [error, setError] = useState(null)
    const [isLoaded, setIsLoaded] = useState(false);
    const [data, setData] = useState([]);
    const [item, setItem] = useState({});

    const handleVehicleType = (e, value) => {
        setItem({
            ...item,
            vehicleType: value.value
        })
    }

    const handleMinutePrice = (e) => {
        setItem({
            ...item,
            minutePrice: e.target.value
        })
    }
    
    const createFare = () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(item)
        };
        fetch(config.config.host+":"+config.config.port+config.config.create, requestOptions)
            .then(
                (res) => {
                    if(!res.ok) throw new Error(res.status)
                    else return res.json()
                }
            ).then(updateScreen())
    }

const vehicleTypes = [
    { key: 'car', text: 'Car', value: 'car' },
    { key: 'motorbike', text: 'Motorbike', value: 'motorbike' },
    { key: 'truck', text: 'Truck', value: 'truck' },
]

const updateScreen = () => {
    fetch(config.config.host+":"+config.config.port+config.config.getAll)  
        .then(
            (res) => {
                if(!res.ok) throw new Error(res.status)
                else return res.json()
            }
        ).then(
            (result) => (
                setIsLoaded(true),
                setData(result)
            ),
            (error) => (
                setIsLoaded(true),
                setError(error)
            )
        )
}

    useEffect(() => {
        updateScreen(config)
    }, [])

    return (
        <Grid celled>
            <Grid.Row>
                <Grid.Column width={8}>
                    <Table celled padded>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell singleLine>Vehicle Type</Table.HeaderCell>
                                <Table.HeaderCell>Price per minute</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {
                                isLoaded ? 
                                    data.map((data)=> {
                                        return (
                                            <Table.Row key={data.idBook}>
                                                <Table.Cell>{data.vehicleType}</Table.Cell>
                                                <Table.Cell>{data.minutePrice}</Table.Cell>
                                            </Table.Row>
                                        )
                                    }) : 
                                    (
                                        <Table.Row>
                                            <Table.Cell></Table.Cell>
                                            <Table.Cell></Table.Cell>
                                        </Table.Row>
                                    )
                            }
                        </Table.Body>
                    </Table>
                </Grid.Column>
                <Grid.Column width={8}>
                    <Form onSubmit={createFare}>
                        <Form.Group widths='equal'>
                            <Form.Select inline
                                iconPosition='left'
                                options={vehicleTypes}
                                label='Vehicle type'
                                placeholder='Type'
                                value={item.vehicleType}
                                onChange={handleVehicleType}
                                />
                            <Form.Input inline
                                icon='eur'
                                iconPosition='left'
                                label='Price/min'
                                placeholder='Price/min'
                                value={item.minutePrice}
                                onChange={handleMinutePrice}
                                />
                        </Form.Group>
                        <Form.Button inline type='submit'>Create</Form.Button>
                    </Form>
                </Grid.Column>
            </Grid.Row>
        </Grid>
    );
}

export default FareCRUD;