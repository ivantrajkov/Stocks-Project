import {Link} from "react-router-dom";
import Home from "./Home";
import React, {useEffect, useState} from "react";
import ReactGaugeChart from "react-gauge-chart";
import '../css/analyze.css';
import NavBar from "../components/NavBar/NavBar";
import Footer from "../components/Footer/Footer";
import useFetchStockSymbols from "../components/hooks/useFetchStockSymbols";
import renderGauge from "../components/utils/Gauge";



const Analyze = () => {
    const { stockSymbols, eror } = useFetchStockSymbols('http://localhost:8080/api/stocks');
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


    // Function to fetch a specific technical indicator from the backend and set the corresponding state
    const fetchIndicator = async (indicator, setState) => {
        try {
            const response = await fetch(`http://localhost:8080/api/indicator?symbol=${selectedStockSymbol}&period=${period}&indicator=${indicator}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch ${indicator} value`);
            }
            const data = await response.json();
            setState(data);
        } catch (error) {
            setError(error.message);
        }
    };
    // Function to fetch the oscillator signal from the backend, which is combined from all oscillators
    const fetchOscillatorSignal = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/oscillators?rsi=${rsiValue}&stochastic=${stochastic}&rateOfChange=${roc}&momentum=${momentum}&chande=${cmo}`);
            if (!response.ok) {
                throw new Error('Failed to fetch oscillator signal');
            }
            const data = await response.text();
            setOscillatorSignal(data);
        } catch (error) {
            setError(error.message);
        }
    };
    // Function to fetch the moving average signal from the backend, which is combined from all moving averages
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

    // useEffect hook to fetch all indicators whenever the selected stock symbol or period changes
    useEffect(() => {
        if (selectedStockSymbol && period) {
            fetchIndicator('rsi', setRsiValue);
            fetchIndicator('stochastic', setStochastic);
            fetchIndicator('roc', setRoc);
            fetchIndicator('momentum', setMomentum);
            fetchIndicator('sma', setSma);
            fetchIndicator('cmo', setCmo);
            fetchIndicator('ema', setEma);
            fetchIndicator('wma', setWma);
            fetchIndicator('tma', setTma);
            fetchIndicator('kama', setKama);
        }
    }, [selectedStockSymbol, period]);

    return (
        <>

            <NavBar/>
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
                    <div className="signals" style={{display: "flex", justifyContent: "center"}}>
                        {renderGauge(oscillatorSignal, 'Oscillator Signal')}
                        {renderGauge(movingAverageSignal, 'Moving Average Signal')}
                    </div>

                </div>


                <div style={{float: "left", textAlign: "left"}}>
                    {rsiValue !== null ? `RSI Index for ${period} day(s): ${rsiValue}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {stochastic !== null ? `Stochastic %K for ${period} day(s): ${stochastic}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {roc !== null ? `Rate of change for ${period} day(s): ${roc}%` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {momentum !== null ? `Momentum for ${period} day(s): ${momentum}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {sma !== null ? `Simple moving average for ${period} day(s): ${sma}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {cmo !== null ? `Chande momentum oscillator for ${period} day(s): ${cmo}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {ema !== null ? `Exponential moving average for ${period} day(s): ${ema}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {wma !== null ? `Weighted moving average for ${period} day(s): ${wma}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {tma !== null ? ` Triangular Moving Average for ${period} day(s): ${tma}` : error ? `Error: ${error}` : "Loading..."}<br/>
                    {kama !== null ? `Kaufman's Adaptive Moving Average, for ${period} day(s): ${kama}` : error ? `Error: ${error}` : "Loading..."}<br/>

                </div>
            </div>

            <div style={{marginRight: "5px"}}>
            <Footer/>
            </div>
        </>
    )
}

export default Analyze;