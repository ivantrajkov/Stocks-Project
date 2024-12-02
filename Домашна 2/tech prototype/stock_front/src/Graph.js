import React, { useEffect, useState } from "react";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { useLocation, Link } from "react-router-dom";
import queryString from "query-string";

const StockGraph = () => {
    const [chartData, setChartData] = useState([]);
    const [error, setError] = useState(null);
    const location = useLocation();
    const { symbol } = queryString.parse(location.search);

    useEffect(() => {
        const fetchData = async () => {
            if (!symbol) return;

            try {
                const response = await fetch(`http://localhost:8080/api/graph?symbol=${symbol}`);
                if (!response.ok) throw new Error("Failed to fetch data");
                const data = await response.json();

                const transformedData = Object.entries(data).map(([year, average]) => ({
                    year,
                    average,
                }));
                setChartData(transformedData);
            } catch (err) {
                setError(err.message);
            }
        };

        fetchData();
    }, [symbol]);

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <>
            <nav className="navbar">
                <img src="/resources/logo.png" alt="StocksAppMK Logo"/>
                <div>
                    <Link to="/">Home</Link>
                    <Link to="/stock-market">Stock market</Link>
                    <Link to="/login">Login</Link>
                </div>
            </nav>
            <ResponsiveContainer width="100%" height={400}>
                <LineChart data={chartData}>
                    <CartesianGrid strokeDasharray="3 3"/>
                    <XAxis dataKey="year" label={{value: "Year", position: "insideBottomRight", offset: -5}}/>
                    <YAxis label={{value: "Average Price", angle: -90, position: "insideLeft"}}/>
                    <Tooltip/>
                    <Legend/>
                    <Line type="monotone" dataKey="average" stroke="#8884d8" activeDot={{r: 8}}/>
                </LineChart>
            </ResponsiveContainer>
            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
            </div>
        </>
    );
};

export default StockGraph;
