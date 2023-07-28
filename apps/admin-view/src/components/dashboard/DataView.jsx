import { Label, Icon } from 'semantic-ui-react'

const DataView = (config) => {

    return (
        <Label size='massive'>
            <Icon name={config.config.icon} /> {config.config.init}
        </Label>
    );

}

export default DataView;