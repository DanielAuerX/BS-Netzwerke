import React, { Component } from 'react';

interface DeviceListState {
    devices: any[];
    searchQuery: string;
}

class DeviceList extends Component {
    state: DeviceListState = {
        devices: [],
        searchQuery: ""
    };

    async componentDidMount() {
        const response = await fetch('/api/danielsEndpoint');
        const data = await response.json();
        this.setState({ devices: data });
    }

    handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({ searchQuery: event.target.value });
    }

    render() {
        const filteredDevices = this.state.devices.filter(device =>
            device.ip.includes(this.state.searchQuery)
        );
        return (
            <div>
                <input
                    type="text"
                    placeholder="Search by IP"
                    onChange={this.handleSearch}
                />
                {filteredDevices.map(device => (
                    <div key={device.macId}>
                        <p>Name: {device.name}</p>
                        <p>System: {device.system}</p>
                        <p>IP: {device.ip}</p>
                    </div>
                ))}
            </div>
        );
    }
}

export default DeviceList;
