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
    showVlanMenu: boolean;
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
        showVlanMenu: false,
    };

    async componentDidMount() {
        if (this.state.selectedEndpoint === 'networks') {
            try {
                const response = await fetch("http://localhost:8080/api/network/networks");
                const data = await response.json();
                this.setState({devices: [], ports: [], switches: [], hosts: [], filteredData: []})
                this.setState({devices: data})
            } catch (error) {
                console.log(error);
            }
        }
        if (this.state.selectedEndpoint === 'ports') {
            try {
                const response = await fetch("http://localhost:8080/api/network/ports");
                const data = await response.json();
                console.log(data);
                this.setState({devices: [], ports: [], switches: [], hosts: [], filteredData: []})
                this.setState({ports: data})
            } catch (error) {
                console.log(error);
            }
        }
        if (this.state.selectedEndpoint === 'switches') {
            try {
                const response = await fetch("http://localhost:8080/api/network/switches");
                const data = await response.json();
                console.log(data);
                this.setState({devices: [], ports: [], switches: [], hosts: [], filteredData: []}) //y data still there??
                this.setState({switches: data})
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
            this.setState({devices: [], ports: [], switches: [], hosts: [], filteredData: []});
            this.setState({filteredData: data});
            console.log(data)
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
            this.setState({devices: [], ports: [], switches: [], hosts: [], filteredData: []});
            this.setState({filteredData: data});
        } catch (error) {
            console.log(error);
        }
    }

    handleVlanMenuButtonClick = () => {
        this.setState({ showVlanMenu: !this.state.showVlanMenu });
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
            this.setState({filteredData: data});
        } catch (error) {
            console.log(error);
        }
    }

    async updateNetworkData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/networks");
            const data = await response.json();

            this.setState({devices: data});
        } catch (error) {
            console.log(error);
        }
    }

    async updatePortData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/ports");
            const data = await response.json();
            this.setState({ports: data});
        } catch (error) {
            console.log(error);
        }
    }

    async updateSwitchData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/switches");
            const data = await response.json();
            console.log(data);
            this.setState({switches: data});
        } catch (error) {
            console.log(error);
        }
    }

    async updateHostData() {
        try {
            const response = await fetch("http://localhost:8080/api/network/hosts");
            const data = await response.json();
            console.log(data);
            this.setState({hosts: data});
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
            host.name.includes(this.state.searchQuery)
        );
        return (
            <div className="App">
                <h1 className="fancy-h1">Select a search</h1>
                <select onChange={this.handleChange} className="fancy-button">
                    <option value="">Select an option</option>
                    <option value="VLAN30">VLAN 30</option>
                    <option value="VLAN20">VLAN 20</option>
                    <option value="1">Switch 1</option>
                    <option value="2">Switch 2</option>
                    <option value="3">Switch 3</option>
                </select>
                <button onClick={() => this.filterDataByVlan()} className="fancy-button">Filter by VLAN</button>
                <button onClick={() => this.filterDataBySwitchId()} className="fancy-button">Filter by Switch ID
                </button>

                <button
                    onClick={() => this.updateNetworkData()}
                    className="fancy-button"
                >Update Department Data
                </button>

                <button onClick={() => this.updatePortData()} className="fancy-button">Update Port Data</button>

                <button onClick={() => this.updateSwitchData()} className="fancy-button">Update Switch Data</button>

                <button onClick={() => this.updateHostData()} className="fancy-button">Update Host Data</button>

                {this.state.filteredData.map((data) => (
                    <div key={data.id} className="search-table">
                        <p><strong>Host: {data.macId}</strong></p>
                        <p>{data.name}</p>
                        <p>{data.ip}</p>
                        <p>{this.state.searchQuery}</p>
                        <br/>
                    </div>
                ))}

                {filteredDevices.map((device) => (
                    <div key={device.id} className="search-table">
                        <p><strong>Department: {device.id}</strong></p>
                        <p>Name: {device.name}</p>
                        <p>Location: {device.location}</p>
                        <br/>
                    </div>
                ))}
                {filteredPorts.map((port) => (
                    <div className="search-table">
                        <br/>
                        <p><strong>Port: {port.name}</strong></p>
                        <p>Switch ID: {port.switchId.name}</p>
                        <p>Port VLAN: {port.vlan}</p>
                        <p>Port Mode: {port.portMode}</p>
                        {port.host && (
                            <>
                                <p>Host: {port.host.name}</p>
                            </>
                        )}
                        <br/>
                    </div>
                ))}
                {filteredSwitches.map((zwitch) => (
                    <div key={zwitch.id} className="search-table">
                        <p><strong>Switch: {zwitch.id}</strong></p>
                        <p>{zwitch.name}</p>
                        <br/>
                    </div>
                ))}
                {filteredHosts.map((host) => (
                    <div key={host.id} className="search-table">
                        <p><strong>Host: {host.macId}</strong></p>
                        <p>Name: {host.name}</p>
                        <p>IP: {host.ip}</p>
                        <p>System: {host.system}</p>
                        <p>Department: {host.department.name}</p>
                        <br/>
                    </div>
                ))}
            </div>
                )
    }
}

export default DeviceList;