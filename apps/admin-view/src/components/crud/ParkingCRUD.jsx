import { useEffect, useState } from "react";
import { Icon, Form, FormGroup, Grid, Table } from 'semantic-ui-react'

const ParkingCRUD = (config) => {
    const [error, setError] = useState(null)
    const [isLoaded, setIsLoaded] = useState(false);
    const [data, setData] = useState([]);
    const [item, setItem] = useState({});
    const [stock, setStock] = useState({});

    const handleStock = (e) => {
        setStock({
            ...stock,
            quantity: e.target.value
        })
    }
    const handlePrice = (e) => {
        setStock({
            ...stock,
            price: e.target.value
        })
    }
    const handleTitle = (e) => {
        setItem({
            ...item,
            title: e.target.value
        })
    }

    const sendStock = (e) => {
        var obj = {}
        if (e.nativeEvent.submitter.id === 'sell') obj.quantity = (stock.quantity * -1)
        else obj.quantity = stock.quantity
        obj.price = parseFloat(stock.price.replace(",", "."))
        obj.type='book'
        obj.id=e.target.dataset.id
        console.log(obj)
    }

    const createBook = () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(item)
        };
        fetch(`http://localhost:8080/book/`, requestOptions)
            .then(
                (res) => {
                    if(!res.ok) throw new Error(res.status)
                    else return res.json()
                }
            ).then(updateScreen)
    }

    const deleteById = (e) => {
        console.log(e.target.dataset.id)
        fetch(`http://localhost:8080/book/`+e.target.dataset.id, { method: 'DELETE' })
            .then(updateScreen)
    }
    
const updateScreen = () => {
    fetch(`https://parking-crud-dev-data.apps.cluster-cdg8z.cdg8z.sandbox1734.opentlc.com/parking`)  
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

    const stringToDate = (str) => {
        if (str == null) return ""
        var options = { 
            month: 'short', 
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric'
          };
        return new Date(str).toLocaleDateString('es-ES',options);
    }

    const minutes = (dateIn, dateOut) => {
        if (dateOut==null)
            return ""
        return new Date(dateIn) - new Date(dateOut) ;
    }

    useEffect(() => {
        updateScreen()
    }, [])

    return (
        <Grid celled>
            <Grid.Row>
                <Grid.Column width={11}>
                    <Table celled padded>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Plate</Table.HeaderCell>
                                <Table.HeaderCell>Type</Table.HeaderCell>
                                <Table.HeaderCell>Entrance</Table.HeaderCell>
                                <Table.HeaderCell>Departure</Table.HeaderCell>
                                <Table.HeaderCell>Minutes</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {
                                isLoaded ? 
                                    data.map((data)=> {
                                        return (
                                            <Table.Row key={data.id}>
                                                <Table.Cell>{data.plate}</Table.Cell>
                                                <Table.Cell>{data.vehicleType}</Table.Cell>
                                                <Table.Cell>{stringToDate(data.entranceDate)}</Table.Cell>
                                                <Table.Cell>{stringToDate(data.departureDate)}</Table.Cell>
                                                <Table.Cell>
                                                    {minutes(data.entranceDate, data.departureDate)}
                                                </Table.Cell>
                                            </Table.Row>
                                        )
                                    }) : 
                                    (
                                        <Table.Row>
                                            <Table.Cell></Table.Cell>
                                            <Table.Cell></Table.Cell>
                                            <Table.Cell></Table.Cell>
                                            <Table.Cell></Table.Cell>
                                        </Table.Row>
                                    )
                            }
                        </Table.Body>
                    </Table>
                </Grid.Column>
                <Grid.Column width={5}>
                    <Form onSubmit={createBook}>
                        <Form.Input
                            icon='pencil alternate'
                            iconPosition='left'
                            label='Title'
                            placeholder='Book title'
                            value={item.title}
                            onChange={handleTitle}
                            />
                        <Form.Button type='submit'>Create</Form.Button>
                    </Form>
                </Grid.Column>
            </Grid.Row>
        </Grid>
    );
}

export default ParkingCRUD;