import React, {Component} from 'react';

interface DeviceListState {
    devices: any[];
    ports: any[];
    searchQuery: string;
}

class DeviceList extends Component {
    state: DeviceListState = {
        devices: [],
        ports: [],
        searchQuery: ""
    };

    async componentDidMount() {
        try {
            const response = await fetch('http://localhost:8080/api/network/networks')
            const data = await response.json();
            this.setState({devices: data});
        } catch (error) {
            console.log(error);
        }
        try {
            const response = await fetch('http://localhost:8080/api/network/ports')
            const data = await response.json();
            console.log(data)
            this.setState({ports: data});
        } catch (error) {
            console.log(error);
        }
    }


    handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({searchQuery: event.target.value});
    }

    render() {
        const filteredDevices = this.state.devices.filter(device =>
            device.name.includes(this.state.searchQuery)
        );
        const filteredPorts = this.state.ports.filter(port =>
            port.name.includes(this.state.searchQuery)
        );
        return (
            <div>
                <input
                    type="text"
                    placeholder="Search by name"
                    onChange={this.handleSearch}
                />
                {filteredDevices.map(device => (
                    <div key={device.id}>
                        <p>Department: {device.id}</p>
                        <p>Name: {device.name}</p>
                        <p>Location: {device.location}</p>
                        <br></br>
                    </div>
                ))}
                {filteredPorts.map(port => (
                    <div>
                        <br></br>
                        <p>Port: {port.id}</p>
                        <p>Switch ID: {port.switchId.name}</p>
                        <p>Port Name: {port.name}</p>
                        <p>Port Mode: {port.portMode}</p>
                        {port.host && (<>
                            <p>Host MAC ID: {port.host.macId}</p>
                            <p>Host Name: {port.host.name}</p>
                            <p>Host IP: {port.host.ip}</p>
                            <p>Host System: {port.host.system}</p>
                            <p> Network: {port.host.network.name} ({port.host.network.location}) </p> </>)} </div>
                ))}
            </div>
        );
    }
}

export default DeviceList;