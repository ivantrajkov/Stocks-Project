import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import NavBar from "../components/NavBar/NavBar";
import Footer from "../components/Footer/Footer";
import "../css/home.css"

const Home = () => {
    const [username, setUsername] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/authentication/loggedIn')
            .then((response) => response.text())
            .then((data) => setUsername(data))
            .catch((error) => console.error('Error getting the username!', error));
    }, []);
    const handleDownloadCSV = () => {
        fetch('http://localhost:8080/csv')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.blob();
            })
            .then((blob) => {
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                link.download = 'data.csv';
                link.click();
                window.URL.revokeObjectURL(url);
            })
            .catch((error) => {
                console.error('Error downloading the CSV file:', error);
            });
    };

    return (
        <>
                <NavBar />

            <div className="hero" style={{height: '350px'}}>
                <h1>StocksAppMK</h1>
                <p>Our site allows you to view and analyze the stock market</p>
                <span style={{fontWeight: 'bold'}}>
                    {username && <i><span>Welcome, {username}</span></i>}
                    {!username && (
                        <>
                            <i><span>Welcome, guest!</span></i>
                        </>
                    )}
                    </span>
                <br/>
                <button onClick={handleDownloadCSV}>Get a CSV file!</button>
            </div>

            <Footer/>
        </>
    );
};

export default Home;
