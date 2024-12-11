import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import './css/home.css';

const Home = () => {
    const [username, setUsername] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/loggedIn')
            .then((response) => response.text())
            .then((data) => setUsername(data))
            .catch((error) => console.error('Error getting the username!', error));
    }, []);

    return (
        <>
            <nav className="navbar">
                <img src="/resources/logo.png" alt="StocksAppMK Logo"/>
                <div>
                    <Link to="/" style={{ backgroundColor: '#d3d3d3' }}>Home</Link>
                    <Link to="/stock-market">Stock market</Link>
                    <Link to="/Analyze">Analyze</Link>
                    <Link to="/Login">Login</Link>
                    <Link to="/Register">Register</Link>

                </div>
            </nav>

            <div className="hero">
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
            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
            </div>
        </>
    );
};

export default Home;
