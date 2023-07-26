import { useEffect, useState } from "react";
import { Grid, Table, Checkbox, Input } from 'semantic-ui-react'

const ParkingCRUD = (config) => {
    const [error, setError] = useState(null)
    const [isLoaded, setIsLoaded] = useState(false);
    const [isParked, setIsParked] = useState(false);
    const [plate, setPlateFilter] = useState("");
    const [data, setData] = useState([]);

    const filterParked = (e, value) => {
        setIsParked(value.checked)
        updateData(value.checked, plate)
    }

    const filterPlate = (e, data) => {
        setPlateFilter(data.value)
        updateData(isParked, data.value)
    }

    const updateData = (parked, plate) => {
        let url = `https://parking-crud-dev-data.apps.cluster-5jm99.5jm99.sandbox2819.opentlc.com/parking`
        if (parked) url+='?parked=true'
        else url+='?parked=false'
        if (plate!=="") url+='&plate='+plate
        
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

    useEffect(() => {
        updateData(false,"")
    }, [])

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

    return (
        <Grid celled>
            <Grid.Row>
                <Grid.Column width={3}>
                    <Checkbox
                        label="Into the parking"
                        onChange={filterParked}
                    />
                </Grid.Column>
                <Grid.Column>
                    <Input
                        label="Plate"
                        icon='search'
                        onChange={filterPlate}
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
                                    data.map((item)=> {
                                        return (
                                            <Table.Row key={item.id}>
                                                <Table.Cell>{item.plate}</Table.Cell>
                                                <Table.Cell>{item.vehicleType}</Table.Cell>
                                                <Table.Cell>{stringToDate(item.entranceDate)}</Table.Cell>
                                                <Table.Cell>{stringToDate(item.departureDate)}</Table.Cell>
                                                <Table.Cell>
                                                    {minutes(item.entranceDate, item.departureDate)}
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