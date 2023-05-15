import { useEffect, useState } from "react";
import { Icon, Form, FormGroup, Grid, Table } from 'semantic-ui-react'

const BookCRUD = (config) => {
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
    fetch(`http://localhost:8080/book`)  
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
        updateScreen()
    }, [])

    return (
        <Grid celled>
            <Grid.Row>
                <Grid.Column width={11}>
                    <Table celled padded>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell singleLine></Table.HeaderCell>
                                <Table.HeaderCell>Title</Table.HeaderCell>
                                <Table.HeaderCell>Stock</Table.HeaderCell>
                                <Table.HeaderCell>Buy/Sell</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {
                                isLoaded ? 
                                    data.map((data)=> {
                                        return (
                                            <Table.Row key={data.idBook}>
                                                <Table.Cell>
                                                    <Icon name="delete" data-id={data.idBook} onClick={ deleteById } />
                                                </Table.Cell>
                                                <Table.Cell>{data.title}</Table.Cell>
                                                <Table.Cell>{data.stock}</Table.Cell>
                                                <Table.Cell>
                                                    <Form data-id={data.idBook} onSubmit={sendStock}>
                                                    <FormGroup>
                                                        <Form.Input inline
                                                            icon='book'
                                                            iconPosition='left'
                                                            type='number'
                                                            placeholder='quantity'
                                                            defaultValue='1'
                                                            onChange={handleStock}
                                                        />
                                                        <Form.Input inline
                                                            icon='euro'
                                                            iconPosition='left'
                                                            type='text'
                                                            placeholder='price'
                                                            defaultValue='6,10'
                                                            onChange={handlePrice}
                                                        />
                                                        <Form.Button id="buy" type='submit'>Compra</Form.Button>
                                                        <Form.Button id="sell" type='submit'>Venta</Form.Button>
                                                    </FormGroup>
                                                    </Form>
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

export default BookCRUD;