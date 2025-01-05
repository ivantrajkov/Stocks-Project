import React, { useEffect, useState } from "react";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { useLocation, Link } from "react-router-dom";
import queryString from "query-string";
import NavBar from "../components/NavBar/NavBar";
import Footer from "../components/Footer/Footer";

const StockGraph = () => {
    const [chartData, setChartData] = useState([]);
    const [error, setError] = useState(null);
    const location = useLocation();
    const { symbol } = queryString.parse(location.search);

    useEffect(() => {
        const fetchData = async () => {
            if (!symbol) return;

            try {
                const response = await fetch(`http://stocks-app:8080/api/graph?symbol=${symbol}`);
                if (!response.ok) throw new Error("Failed to fetch data");
                const data = await response.json();

                const transformedData = Object.entries(data).map(([year, average]) => ({
                    year,
                    average: parseFloat(average).toFixed(2),
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
            <NavBar/>
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
            <Footer/>
        </>
    );
};

export default StockGraph;
