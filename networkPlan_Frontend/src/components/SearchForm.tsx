import React, {ChangeEvent, Component} from "react";

interface DeviceListState {
    devices: any[];
    ports: any[];
    switches: any[];
    hosts: any[];
    searchQuery: string;
    filteredData: any[];
    filterBy: string;
    selectedEndpoint: string;
}

class DeviceList extends Component {
    state: DeviceListState = {
        devices: [],
        ports: [],
        switches: [],
        hosts: [],
        searchQuery: "",
        filteredData: [],
        filterBy: "vlan",
        selectedEndpoint: "",
    };

    async componentDidMount() {
        if (this.state.selectedEndpoint === 'networks') {
            try {
                const response = await fetch("http://localhost:8080/api/network/networks");
                const data = await response.json();
                this.setState({ devices: data });
            } catch (error) {
                console.log(error);
            }
        }
        if (this.state.selectedEndpoint === 'ports') {
            try {
                const response = await fetch("http://localhost:8080/api/network/ports");
                const data = await response.json();
                console.log(data);
                this.setState({ ports: data });
            } catch (error) {
                console.log(error);
            }
        }
        if (this.state.selectedEndpoint === 'switches') {
            try {
                const response = await fetch("http://localhost:8080/api/network/switches");
                const data = await response.json();
                console.log(data);
                this.setState({ switches: data });
            } catch (error) {
                console.log(error);
            }
        }
    }

    filterDataByVlan = async () => {
        try {
            const response = await fetch(
                "http://localhost:8080/api/network/hosts-by-vlan?vlan=" + this.state.searchQuery
            );
            console.log(this.state.searchQuery)
            const data = await response.json();
            this.setState({ filteredData: data });
        } catch (error) {
            console.log(error);
        }
    }

    filterDataBySwitchId = async () => {
        try {
            const response = await fetch(
                "http://localhost:8080/api/network/hosts-by-switch?switchId=" + this.state.searchQuery
            );
            console.log(this.state.searchQuery)
            const data = await response.json();
            this.setState({ filteredData: data });
        } catch (error) {
            console.log(error);
        }
    }

    handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        this.setState({searchQuery: event.target.value});
    }

    handleFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({filterBy: event.target.value});
    }

    async filterData() {
        try {
            let response;
            if (this.state.filterBy === 'vlan') {
                response = await fetch(
                    `http://localhost:8080/api/network/hosts-by-vlan?vlan=${this.state.searchQuery}`
                );
            } else {
                response = await fetch(
                    `http://localhost:8080/api/network/hosts-by-switch?switchId=${this.state.searchQuery}`
                );
            }
            const data = await response.json();
            this.setState({ filteredData: data });
        } catch (error) {
            console.log(error);
        }
    }

    async updateNetworkData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/networks");
            const data = await response.json();
            this.setState({ devices: data });
        } catch (error) {
            console.log(error);
        }
    }

    async updatePortData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/ports");
            const data = await response.json();
            this.setState({ ports: data });
        } catch (error) {
            console.log(error);
        }
    }

    async updateSwitchData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/switches");
            const data = await response.json();
            console.log(data);
            this.setState({ switches: data });
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
                <select onChange={this.handleChange}>
                    <option value="">Select an option</option>
                    <option value="VLAN30">VLAN 30</option>
                    <option value="VLAN20">VLAN 20</option>
                    <option value="1">Switch 1</option>
                    <option value="switch2">Switch 2</option>
                </select>
                <button onClick={() => this.filterDataByVlan()}>Filter by VLAN</button>
                <button onClick={() => this.filterDataBySwitchId()}>Filter by Switch ID</button>
                {this.state.filteredData.map((data) => (
                    <div key={data.id}>
                        <p>Name: {data.name}</p>
                        <p>VLAN: {this.state.searchQuery}</p>
                        <br />
                    </div>
                ))}
                <button onClick={() => this.updateNetworkData}>Update Network Data</button>
                {this.state.devices.map((devices) => {
                    return <div>{devices.name}</div>;
                })}
                <button onClick={() => this.updatePortData()}>Update Port Data</button>
                {this.state.ports.map((port) => {
                    return <div>{port.name}</div>;
                })}
                <button onClick={() => this.updateSwitchData}>Update Switch Data</button>
                {this.state.switches.map((switches) => {
                    return <div>{switches.name}</div>;
                })}
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
