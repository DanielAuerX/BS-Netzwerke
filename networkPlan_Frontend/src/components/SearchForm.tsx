import React, { Component } from "react";

interface DeviceListState {
    devices: any[];
    ports: any[];
    switches: any[];
    hosts: any[];
    searchQuery: string;
    filteredData: any[];
}

class DeviceList extends Component {
    state: DeviceListState = {
        devices: [],
        ports: [],
        switches: [],
        hosts: [],
        searchQuery: "",
        filteredData: [],
    };

    async componentDidMount() {
        try {
            const response = await fetch("http://localhost:8080/api/network/networks");
            const data = await response.json();
            this.setState({ devices: data });
        } catch (error) {
            console.log(error);
        }
        try {
            const response = await fetch("http://localhost:8080/api/network/ports");
            const data = await response.json();
            console.log(data);
            this.setState({ ports: data });
        } catch (error) {
            console.log(error);
        }
        try {
            const response = await fetch("http://localhost:8080/api/network/switches");
            const data = await response.json();
            console.log(data);
            this.setState({ switches: data });
        } catch (error) {
            console.log(error);
        }
        try {
            const response = await fetch(
                `http://localhost:8080/api/network/hosts-by-vlan?vlan=${this.state.searchQuery}`
            );
            const data = await response.json();
            console.log(data);
            this.setState({ hosts: data });
        } catch (error) {
            console.log(error);
        }
    }

    handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({searchQuery: event.target.value});
    }

    async filterDataByVlan() {
        try {
            const response = await fetch(
                "http://localhost:8080/api/network/hosts-by-vlan?vlan=" + this.state.searchQuery
            );
            console.log(this.state.searchQuery)
            const data = await response.json();
            this.setState({ filteredData: data, isVlan: true });
        } catch (error) {
            console.log(error);
        }
    }

    async filterDataBySwitchId() {
        try {
            const response = await fetch(
                "http://localhost:8080/api/network/hosts-by-switch?switchId=" + this.state.searchQuery
            );
            console.log(this.state.searchQuery)
            const data = await response.json();
            this.setState({ filteredData: data, isVlan: false });
        } catch (error) {
            console.log(error);
        }
    }

    render() {
        const filteredDevices = this.state.devices.filter((device) =>
            device.name.includes(this.state.searchQuery)
        );
        const filteredPorts = this.state.ports.filter((port) =>
            port.vlan.includes(this.state.searchQuery)
        );
        const filteredSwitches = this.state.switches.filter((zwitch) =>
            zwitch.name.includes(this.state.searchQuery)
        );
        const filteredHosts = this.state.hosts.filter((host) =>
            host.vlan.includes(this.state.searchQuery)
        );
        return (
            <div>
                <input
                    type="text"
                    placeholder="Enter VLAN"
                    onChange={this.handleChange}
                />
                <button onClick={() => this.filterDataByVlan()}>Filter</button>
                {this.state.filteredData.map((data) => (
                    <div key={data.id}>
                        <p>Name: {data.name}</p>
                        <p>VLAN: {this.state.searchQuery}</p>
                        <br />
                    </div>
                ))}
                <input
                    type="text"
                    placeholder="Enter SwitchID"
                    onChange={this.handleChange}
                />
                <button onClick={() => this.filterDataBySwitchId()}>Filter</button>
                {this.state.filteredData.map((data) => (
                    <div key={data.id}>
                        <p>Name: {data.name}</p>
                        <p>VLAN: {data.vlan}</p>
                        <p>Location: {data.location}</p>
                        <br />
                    </div>
                ))}
                {filteredDevices.map((device) => (
                    <div key={device.id}>
                        <p>Department: {device.id}</p>
                        <p>Name: {device.name}</p>
                        <p>Location: {device.location}</p>
                        <br />
                    </div>
                ))}
                {filteredPorts.map((port) => (
                    <div>
                        <br />
                        <p>Port: {port.id}</p>
                        <p>Switch ID: {port.switchId.name}</p>
                        <p>Port Name: {port.name}</p>
                        <p>Port VLAN: {port.vlan}</p>
                        <p>Port Mode: {port.portMode}</p>
                        {port.host && (
                            <>
                                <p>Host MAC ID: {port.host.macId}</p>
                                <p>Host Name: {port.host.name}</p>
                                <p>Host IP: {port.host.ip}</p>
                                <p>Host System: {port.host.system}</p>
                                <p>
                                    Department:{" "}
                                    {port.host.department.name} (
                                    {port.host.department.location})
                                </p>
                            </>
                        )}
                        <br />
                    </div>
                ))}
                {filteredSwitches.map((zwitch) => (
                    <div key={zwitch.id}>
                        <p>Switch: {zwitch.name}</p>
                        <p>Switch ID: {zwitch.id}</p>
                        <p>Location: {zwitch.location}</p>
                        <br />
                    </div>
                ))}
                {filteredHosts.map((host) => (
                    <div key={host.id}>
                        <p>Host: {host.name}</p>
                        <p>MAC ID: {host.macId}</p>
                        <p>IP: {host.ip}</p>
                        <p>System: {host.system}</p>
                        <p>Department: {host.department.name}</p>
                        <br />
                    </div>
                ))}
            </div>
        );
    }
}

export default DeviceList;
