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
    const [roc, setRoc] = useState(null);
    const [momentum, setMomentum] = useState(null);
    const [sma,setSma] = useState(null);
    const [cmo, setCmo] = useState(null);
    const [ema, setEma] = useState(null);
    const [wma,setWma] = useState(null);
    const [tma,setTma] = useState(null);
    const [kama,setKama] = useState(null);
    const [oscillatorSignal, setOscillatorSignal] = useState(null);
    const [movingAverageSignal, setMovingAverageSignal] = useState(null);

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
    const fetchRateOfChange = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/roc?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch rate of change value');
            }
            const data = await response.json();
            setRoc(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchMomentum = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/momentum?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch rate of change value');
            }
            const data = await response.json();
            setMomentum(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchSimpleMovingAverage = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/sma?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch rate of change value');
            }
            const data = await response.json();
            setSma(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchCMO = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/cmo?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch rate of change value');
            }
            const data = await response.json();
            setCmo(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchEMA = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/ema?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch EMA value');
            }
            const data = await response.json();
            setEma(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchWMA = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/wma?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch EMA value');
            }
            const data = await response.json();
            setWma(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchTMA = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/tma?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch EMA value');
            }
            const data = await response.json();
            setTma(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchKAMA = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/kama?symbol=${selectedStockSymbol}&period=${period}`);
            if (!response.ok) {
                throw new Error('Failed to fetch EMA value');
            }
            const data = await response.json();
            setKama(data);
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchOscillatorSignal = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/oscillators?rsi=${rsiValue}&stochastic=${stochastic}&rateOfChange=${roc}&momentum=${momentum}&chande=${cmo}`);
            if (!response.ok) {
                throw new Error('Failed to fetch oscillator signal');
            }
            const data = await response.text();
            setOscillatorSignal(data);
            // console.log(`http://localhost:8080/api/oscillators?rsi=${rsiValue}&stochastic=${stochastic}&rateOfChange=${roc}&momentum=${momentum}&chande=${cmo}`)
        } catch (error) {
            setError(error.message);
        }
    };
    const fetchMovingAverageSignal = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/movingAverages?symbol=${selectedStockSymbol}&sma=${sma}&ema=${ema}&wma=${wma}&tma=${tma}&kama=${kama}`);
            if (!response.ok) {
                throw new Error('Failed to fetch moving average signal');
            }
            const data = await response.text();
            setMovingAverageSignal(data);
        } catch (error) {
            setError(error.message);
        }
    };


    useEffect(() => {
        if (selectedStockSymbol && period){
            fetchRsiValue();
            fetchStochasticValue();
            fetchRateOfChange();
            fetchMomentum();
            fetchSimpleMovingAverage();
            fetchCMO();
            fetchEMA();
            fetchWMA();
            fetchTMA();
            fetchKAMA();

        }
    }, [selectedStockSymbol,period,cmo]);

    // useEffect(() => {
    //     console.log(rsiValue + " " + momentum + " " + stochastic + " " + cmo + " " + roc)
    //     if(rsiValue && momentum && stochastic && cmo && roc){
    //         fetchOscillatorSignal();
    //     }
    //     fetchOscillatorSignal();
    // }, [rsiValue, cmo,stochastic,momentum,roc]);






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
                    <button
                        onClick={() => {
                            fetchOscillatorSignal();
                            fetchMovingAverageSignal();
                        }}
                    >
                        Overall
                    </button>

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
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {roc !== null ? `Rate of change for ${period} day(s): ${roc}%` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {momentum !== null ? `Momentum for ${period} day(s): ${momentum}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {sma !== null ? `Simple moving average for ${period} day(s): ${sma}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {cmo !== null ? `Chande momentum oscillator for ${period} day(s): ${cmo}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {ema !== null ? `Exponential moving average for ${period} day(s): ${ema}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {wma !== null ? `Weighted moving average for ${period} day(s): ${wma}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {tma !== null ? ` Triangular Moving Average for ${period} day(s): ${tma}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
                <br/>
                <div style={{float: "left"}}>
                    <div>
                        {kama !== null ? `Kaufman's Adaptive Moving Average, for ${period} day(s): ${kama}` : error ? `Error: ${error}` : "Loading..."}
                    </div>
                </div>
            </div>
            <div class={"analyzeGraph"}>
                {oscillatorSignal != null ? `Oscillator signal is: ${oscillatorSignal}` : error ? `Error: ${error}` : "Loading..."}
                {movingAverageSignal != null ? `Moving averages signal is: ${movingAverageSignal}` : error ? `Error: ${error}` : "Loading..."}
            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
            </div>

        </>
    )
}

export default Analyze;