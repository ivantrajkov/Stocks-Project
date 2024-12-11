import {Link} from "react-router-dom";
import Home from "./Home";
import React, {useEffect, useState} from "react";



const Analyze = () => {
    const [stockSymbols, setStockSymbols] = useState([]);
    const [error, setError] = useState(null);
    const [selectedStockSymbol, setSelectedStockSymbol] = useState('ADIN');
    const [rsiValue, setRsiValue] = useState(null);
    const [period, setPeriod] = useState(1);
    const [stochastic, setStochastic] = useState(null);

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
            const response = await fetch(`http://localhost:8080/api/rsi?symbol=${selectedStockSymbol}&period=${period}`);
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
    const fetchStochasticValue = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/stochastic?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch stochatic %K value');
            }
            const data = await response.json();
            setStochastic(data);
        } catch (error) {
            setError(error.message);
        }
    };

    useEffect(() => {
        if (selectedStockSymbol || period){
            fetchRsiValue();
            fetchStochasticValue();
        }
    }, [selectedStockSymbol,period,stochastic]);

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
                <div className={"optionContainer"}>
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
                    <h5>Select a period of time in days:</h5>
                    <select onChange={(e) => {
                        setPeriod(parseInt(e.target.value));
                    }}>
                        <option>1</option>
                        <option>7</option>
                        <option>30</option>
                    </select>
                </div>


                <div style={{float: "left"}}>
                    <div>
                        {rsiValue !== null ? `RSI Index for ${period} day(s): ${rsiValue}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {stochastic !== null ? `Stochastic %K for ${period} day(s): ${stochastic}` : error ? `Error: ${error}` : "Loading..."}
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