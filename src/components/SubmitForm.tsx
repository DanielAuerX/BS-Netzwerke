import React, { useState } from 'react';

function App() {
    const [ip, setIp] = useState('');
    const [mask, setMask] = useState('');
    const [amount, setAmount] = useState('');
    const [ips, setIps] = useState([]);
    const [error, setError] = useState('');
    const resetIps = () => {
        setIps([]);
    }

    async function handleSubmit(){
        // @ts-ignore
        // eslint-disable-next-line no-restricted-globals
        event.preventDefault();
        // @ts-ignore
        if (amount < 3) {
            setError('Please enter at least three');
            return;
        }
        setError('');
        const response = await fetch('http://localhost:8080/api/calculator', {
            method: 'POST',
            body: JSON.stringify({ ip, mask, amount }),
            headers: { 'Content-Type': 'application/json' },
        })
            .then((res) => res.json())
            .then((data) => setIps(data));
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    IP:
                    <input
                        type="text"
                        value={ip}
                        onChange={(e) => setIp(e.target.value)}
                    />
                </label>
                <br />
                <label>
                    Mask:
                    <input
                        type="text"
                        value={mask}
                        onChange={(e) => setMask(e.target.value)}
                    />
                </label>
                <br />
                <label>
                    Amount:
                    <input
                        type="text"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                </label>
                <br />
                <button type="submit">Submit</button>
            </form>
            {error && <div style={{color: "red"}}>{error}</div>}

            <div>
                <button onClick={resetIps}>Reset</button>
            </div>

            <h1>IP Addresses</h1>
            {ips.map((ip, index) => (
                <li key={ip}>
                    {index === 0 ? "Network IP: " : index === ips.length - 1 ? "Broadcast IP: " : "Possible IP: "}
                    {ip}
                </li>
            ))}
        </div>

    );
}

export default App;