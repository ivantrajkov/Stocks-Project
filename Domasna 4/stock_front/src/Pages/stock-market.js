import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import '../css/stock-market.css';
import NavBar from "../components/NavBar/NavBar";
// import useFetchStockSymbols from '../hooks/useFetchStockSymbols';

const StockMarket = () => {
    const [tableData, setTableData] = useState([]);
    const [filteredData, setFilteredData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [stockSymbols, setStockSymbols] = useState([]);

    const [stockSymbolFilter, setStockSymbolFilter] = useState('');
    const [selectedStockSymbol, setSelectedStockSymbol] = useState('ZPOG');
    const [averagePriceFilter, setAveragePriceFilter] = useState('');
    const [fromDateFilter, setFromDateFilter] = useState('');
    const [toDateFilter, setToDateFilter] = useState('');

    const reverseDateFormat = (date) => {
        const [year, month, day] = date.split('-');
        return `${day}.${month}.${year}`;
    };

    const formatNumber = (number) => {
        return new Intl.NumberFormat('mk-MK').format(number);
    };


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

    useEffect(() => {
        const fetchInitialStockData = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/all');
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                const data = await response.json();
                setTableData(data);
                setFilteredData(data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchInitialStockData();
    }, []);

    const handleFilter = async () => {
        if (!stockSymbolFilter && !averagePriceFilter && !fromDateFilter && !toDateFilter) {
            alert('Enter atleast one filter!');
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/all?symbol=${stockSymbolFilter}&avgPrice=${averagePriceFilter}&fromDate=${fromDateFilter}&toDate=${toDateFilter}`
            );
            if (!response.ok) {
                throw new Error('Failed to fetch filtered data');
            }
            const data = await response.json();
            console.log("Filtered Data:", data);
            setFilteredData(data);
        } catch (error) {
            setError(error.message);
        }
    };

    const renderTableRows = () => {
        const rowsToDisplay = filteredData.slice(0, 50);
        return rowsToDisplay.map((row, index) => (
            <tr key={index}>
                <td>{reverseDateFormat(row.date)}</td>
                <td>{formatNumber(row.lastTransactionPrice)}</td>
                <td>{formatNumber(row.maxPrice)}</td>
                <td>{formatNumber(row.minPrice)}</td>
                <td>{formatNumber(row.averagePrice)}</td>
                <td>{formatNumber(row.percentageChange)}</td>
                <td>{formatNumber(row.quantity)}</td>
                <td>{formatNumber(row.bestTurnover)}</td>
                <td>{formatNumber(row.totalTurnover)}</td>
                <td>{row.stockSymbol}</td>
            </tr>
        ));
    };

    return (
        <>

            <NavBar/>

            <div className="table-section">
                <h2>Stock view</h2>
                <div className="filter">
                    <input
                        type="text"
                        placeholder="Filter by stock symbol"
                        value={stockSymbolFilter}
                        onChange={(e) => setStockSymbolFilter(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Filter by the lowest average price"
                        value={averagePriceFilter}
                        onChange={(e) => setAveragePriceFilter(e.target.value)}
                    />
                    <input
                        type="date"
                        placeholder="Start date:"
                        value={fromDateFilter}
                        onChange={(e) => setFromDateFilter(e.target.value)}
                    />
                    <input
                        type="date"
                        placeholder="End date:"
                        value={toDateFilter}
                        onChange={(e) => setToDateFilter(e.target.value)}
                    />
                    <button onClick={handleFilter}>Filter</button>
                    <div style={{float: 'right', marginRight: '100px', marginLeft: '10px'}}>
                        <h3>Choose a stock to draw a graph</h3>
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
                        <Link to={`/Graph?symbol=${selectedStockSymbol}`} id={"btnDraw"}>Draw</Link>
                    </div>
                </div>

                {loading && <p>Loading...</p>}

                {error && <p>Error: {error}</p>}

                {!loading && !error && (
                    <table>
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Last Transaction Price</th>
                            <th>Maximum Price</th>
                            <th>Minimum Price</th>
                            <th>Average Price</th>
                            <th>Percentage Change</th>
                            <th>Quantity</th>
                            <th>Best Turnover</th>
                            <th>Total Turnover</th>
                            <th>Stock Symbol</th>
                        </tr>
                        </thead>
                        <tbody>
                        {renderTableRows()}
                        </tbody>
                    </table>
                )}
            </div>

        </>
    );
};


export default StockMarket;
