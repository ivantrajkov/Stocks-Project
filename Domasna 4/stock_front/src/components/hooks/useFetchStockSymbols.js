import { useState, useEffect } from 'react';
//Returns the stock symbols from the backend
const useFetchStockSymbols = (url) => {
    const [stockSymbols, setStockSymbols] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStockSymbols = async () => {
            try {
                const response = await fetch(url);
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
    }, [url]);

    return { stockSymbols, error };
};

export default useFetchStockSymbols;
