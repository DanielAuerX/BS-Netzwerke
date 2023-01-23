import React, {Component} from 'react';

interface DeviceListState {
    devices: any[];
    ports: any[];
    switches: any[];
    hosts: any[],
    searchQuery: string;
}

class DeviceList extends Component {
    state: DeviceListState = {
        devices: [],
        ports: [],
        switches: [],
        hosts: [],
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
        try{
            const response = await fetch('http://localhost:8080/api/network/switches')
            const data = await response.json();
            console.log(data)
            this.setState({switches: data});
        }catch(error){
            console.log(error)
        }
        try{
            const response = await fetch("http://localhost:8080/api/network/hosts-by-vlan?vlan=" + this.state.searchQuery)
            const data = await response.json();
            console.log(data)
            this.setState({ports: data})
        }catch(error){
            console.log(error)
        }
    }



    handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({searchQuery: event.target.value});
        this.componentDidMount();
    }



    render() {
        const filteredDevices = this.state.devices.filter(device =>
            device.name.includes(this.state.searchQuery)
        );
        const filteredPorts = this.state.ports.filter(port =>
            port.name.includes(this.state.searchQuery)
        );
        const filteredSwitches = this.state.switches.filter(zwitch =>
            zwitch.name.includes(this.state.searchQuery)
        );
        const filteredHosts = this.state.hosts.filter(host =>
            host.vlan.includes(this.state.searchQuery)
        );
        return (
            <div>
                <input
                    type="text"
                    placeholder="Search by VLAN"/>
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
                        <p>Port VLAN: {port.vlan}</p>
                        <p>Port Mode: {port.portMode}</p>
                        {port.host && (<>
                            <p>Host MAC ID: {port.host.macId}</p>
                            <p>Host Name: {port.host.name}</p>
                            <p>Host IP: {port.host.ip}</p>
                            <p>Host System: {port.host.system}</p>
                            <p> Department: {port.host.department.name} ({port.host.department.location}) </p> </>)} </div>
                ))}
                {filteredSwitches.map(zwitch => (
                    <div>
                        <br></br>
                        <p>Switch: {zwitch.id}</p>
                        <p>Name: {zwitch.name}</p>
                    </div>
                ))}
                <table>
                    <thead>
                    <tr>
                        <th>VLAN</th>
                        <th>IP</th>
                        <th>MAC</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredHosts.length > 0 && filteredHosts.map(host => (
                        <tr key={host.id}>
                            <td>{host.vlan}</td>
                            <td>{host.ip}</td>
                            <td>{host.mac}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default DeviceList;