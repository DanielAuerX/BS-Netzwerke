import React, { useState, SyntheticEvent } from 'react';

function App() {
    const [ip, setIp] = useState('');
    const [mask, setMask] = useState('');
    const [amount, setAmount] = useState('');
    const [subnets, setSubnets] = useState<[string, string][]>([]);
    const [error, setError] = useState('');

    async function handleSubmit(event: SyntheticEvent){
        event.preventDefault();
        setError('');
        await fetch('http://localhost:8080/api/calculator', {
            method: 'POST',
            body: JSON.stringify({ ip, mask, amount }),
            headers: { 'Content-Type': 'application/json' },
        })
            .then((res) => res.json())
            .then((data) => {
                const subnetsArray = Object.entries(data) as unknown as [string, unknown][];
                subnetsArray.sort((a, b) => {
                    const ipA = a[0].split(".").map(Number);
                    const ipB = b[0].split(".").map(Number);
                    for (let i = 0; i < 4; i++) {
                        if (ipA[i] !== ipB[i]) {
                            return ipA[i] - ipB[i];
                        }
                    }
                    return 0;
                });
                setSubnets(subnetsArray as [string, string][]);
            });

    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    IP:
                    <input
                        type="text"value={ip}
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
            <h1>Subnets</h1>
            <table>
                <thead>
                <tr>
                    <th>IP</th>
                    <th>Broadcast</th>
                </tr>
                </thead>
                <tbody>
                {subnets.map((subnet, index) => (
                    <tr key={index}>
                        <td>{subnet[0]}</td>
                        <td>{subnet[1]}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default App;
