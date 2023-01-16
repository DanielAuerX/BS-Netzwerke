import React, { useState, useEffect } from 'react';
import { IState as Props } from "../App";
import List from "./IpList";

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
        <div>
            <h1>IP Addresses</h1>
            <ul>
                {ips.map((ip) => (
                    <li key={ip}>{ip}</li>
                ))}
            </ul>
        </div>
    );
}

export default DisplayIps;
