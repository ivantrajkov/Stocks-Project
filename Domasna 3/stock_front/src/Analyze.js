import {Link} from "react-router-dom";
import Home from "./Home";
import React, {useEffect, useState} from "react";



const Analyze = () => {
    const [stockSymbols, setStockSymbols] = useState([]);
    const [error, setError] = useState(null);
    const [selectedStockSymbol, setSelectedStockSymbol] = useState('ADIN');
    const [rsiValue, setRsiValue] = useState(null);

    useEffect(() => {
        const fetchStockSymbols = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/stocks');
                if (!response.ok) {
                    throw new Error('Failed to fetch stock symbols');
                }
                const data = await response.json();
                setStockSymbols(data);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchStockSymbols();
    }, []);

    const fetchRsiValue = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/rsi?symbol=${selectedStockSymbol}`);
            if (!response.ok) {
                throw new Error('Failed to fetch RSI value');
            }
            const data = await response.json();
            setRsiValue(data);
            console.log(rsiValue);
        } catch (error) {
            setError(error.message);
        }
    };

    useEffect(() => {
        if (selectedStockSymbol) {
            console.log(`Fetching RSI for symbol: ${selectedStockSymbol}`);
            fetchRsiValue();
        }
    }, [selectedStockSymbol]);

    return (
        <>
            <nav className="navbar">
                <img src="/resources/logo.png" alt="StocksAppMK Logo"/>
                <div>
                    <Link to="/" style={{backgroundColor: '#d3d3d3'}}>Home</Link>
                    <Link to="/stock-market">Stock market</Link>
                    <Link to="/Analyze">Analyze</Link>
                    <Link to="/Login">Login</Link>
                    <Link to="/Register">Register</Link>
                </div>
            </nav>
            <div className={"hero"}>
                <h3>Choose a stock to analyze</h3>
                <select
                    value={selectedStockSymbol}
                    onChange={(e) => {
                        setSelectedStockSymbol(e.target.value);
                    }}
                >
                    {stockSymbols.map((symbol, index) => (
                        <option key={index} value={symbol}>
                            {symbol}
                        </option>
                    ))}
                </select>


                <div style={{float: "left"}}>
                    <div>
                        {rsiValue !== null ? `RSI Index: ${rsiValue}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>


            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
            </div>

        </>
    )
}

export default Analyze;