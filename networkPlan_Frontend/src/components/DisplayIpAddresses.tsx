import React, { useState, useEffect } from 'react';
import { IState as Props } from "../App";
import List from "./IpList";
import logo from './logo.png';

interface IProps {
    setAddresses: React.Dispatch<React.SetStateAction<Props["addresses"]>>
    addresses: Props["addresses"]
}

function DisplayIps() {
    const [ips, setIps] = useState([]);

    useEffect(() => {
        fetch('/calculator', {
            method: 'POST',
            body: JSON.stringify({ ip: '192.168.1.1', mask: '255.255.255.0', amount: 5 }),
            headers: { 'Content-Type': 'application/json' },
        })
            .then((res) => res.json())
            .then((data) => setIps(data));
    }, []);

    return (
        <div className="App">
            <h1>Welcome to GEEK Fitness GmbH</h1>
            <img src={logo} alt="Logo" style={{width: '250px', height: '180px'}}/>
            <p>What do you want to do?</p>
            <div className="buttons">
            <a href="/form">
                <button>Submask Calculator</button>
            </a>
            <a href="/search">
                <button>Search network information</button>
            </a>
            </div>
            <ul>
                {ips.map((ip) => (
                    <li key={ip}>{ip}</li>
                ))}
            </ul>
        </div>
    );
}

export default DisplayIps;
