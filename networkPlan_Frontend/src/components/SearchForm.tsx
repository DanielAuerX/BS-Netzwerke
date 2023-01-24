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
    selectedOption: string;
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
        selectedOption: ""
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

    handleSelectionVlan = async (event: React.ChangeEvent<HTMLSelectElement>) => {
        if (event && event.target) {
            console.log(event.target.value)
            try {
                const response = await fetch(
                    "http://localhost:8080/api/network/hosts-by-vlan?vlan=" + event.target.value
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
    };

    handleSelectionSwitch = async (event: React.ChangeEvent<HTMLSelectElement>) => {
        if (event && event.target) {
            try {
                const response = await fetch(
                    "http://localhost:8080/api/network/hosts-by-switch?switchId=" + event.target.value
                );
                console.log(this.state.searchQuery)
                const data = await response.json();
                this.setState({devices: [], ports: [], switches: [], hosts: [], filteredData: []});
                this.setState({filteredData: data});
            } catch (error) {
                console.log(error);
            }
        }
    };

    handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.value === "allPorts") {
            this.updatePortData();
        }
        if (e.target.value === "allSwitches") {
            this.updateSwitchData();
        }
        if (e.target.value === "allDepartments") {
            this.updateNetworkData();
        }
        if (e.target.value === "allHosts") {
            this.updateHostData();
        }
        if (e.target.value === "hostsByVlan") {
            // eslint-disable-next-line no-restricted-globals
            // @ts-ignore
            // eslint-disable-next-line no-restricted-globals
            this.setState({ selectedOption: event.target.value });
        }
        if (e.target.value === "hostBySwitch") {
            // eslint-disable-next-line no-restricted-globals
            // @ts-ignore
            // eslint-disable-next-line no-restricted-globals
            this.setState({ selectedOption: event.target.value });
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
                    <option value="">Select a search</option>
                    <option value="allPorts">Find all ports</option>
                    <option value="allSwitches">Find all switches</option>
                    <option value="allDepartments">Find all departments</option>
                    <option value="allHosts">Find all hosts</option>
                    <option value="hostsByVlan">Find hosts by VLAN</option>
                    <option value="hostBySwitch">Find hosts by switch ID</option>
                </select>
                {this.state.selectedOption === "hostsByVlan" && (
                    <select onChange={this.handleSelectionVlan} className="fancy-button">
                        <option value="">Select a VLAN</option>
                        <option value="VLAN20">VLAN 20</option>
                        <option value="VLAN30">VLAN 30</option>
                    </select>
                )}
                {this.state.selectedOption === "hostBySwitch" && (
                    <select onChange={this.handleSelectionSwitch} className="fancy-button">
                        <option value="">Select the switch ID</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                )}

                {this.state.filteredData.map((data) => (
                    <div key={data.id} className="search-table">
                        <p><strong>Host: {data.macId}</strong></p>
                        <p>Name: {data.name}</p>
                        <p>IP: {data.ip}</p>
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
                        <p>VLAN: {port.vlan}</p>
                        <p>Mode: {port.portMode}</p>
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
                        <p>Name: {zwitch.name}</p>
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