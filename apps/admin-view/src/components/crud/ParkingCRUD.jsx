import { useEffect, useState } from "react";
import { Icon, Form, FormGroup, Grid, Table, Checkbox } from 'semantic-ui-react'

const ParkingCRUD = (config) => {
    const [error, setError] = useState(null)
    const [isLoaded, setIsLoaded] = useState(false);
    const [isParked, setIsParked] = useState({});
    const [data, setData] = useState([]);
    const [item, setItem] = useState({});
    const [stock, setStock] = useState({});

    const filterParked = (e, value) => {
        setIsParked(value.checked)
        updateScreen()
    }

    const handleStock = (e) => {
        setStock({
            ...stock,
            quantity: e.target.value
        })
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
    let url = `https://parking-crud-dev-data.apps.cluster-cdg8z.cdg8z.sandbox1734.opentlc.com/parking`
    if (isParked) url+='?parked=true'
    fetch(url)  
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
            dateOut=Date.now()
        return Math.round(Math.abs(new Date(dateIn) - new Date(dateOut))/1000/60);
    }

    useEffect(() => {
        updateScreen()
    }, [])

    return (
        <Grid celled>
            <Grid.Row>
                <Grid.Column width={11}>
                    <Checkbox
                        label="Into the parking"
                        onChange={filterParked}
                    />
                </Grid.Column>
            </Grid.Row>
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
            </Grid.Row>
        </Grid>
    );
}

export default ParkingCRUD;