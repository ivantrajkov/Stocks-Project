import React from 'react';
import { Link } from "react-router-dom";
import './css/home.css';

const Home = () => {
    return (
        <>
            {/* Navbar */}
            <nav className="navbar">
                <img src="/resources/logo.png" alt="StocksAppMK Logo"/>
                <div>
                    <Link to="/" style={{ backgroundColor: '#d3d3d3' }}>Home</Link>
                    <Link to="/stock-market">Stock market</Link>
                    <Link to="/Analyze">Analyze</Link>
                    <Link to="/Login">Login</Link>
                    {/*<Link to="/register" className="highlighted-link">Регистрација</Link>*/}
                </div>
            </nav>


            <div className="hero">
                <h1>StocksAppMK</h1>
                <p>Our site allows you to view and analyze the stock market</p>
            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width:'250px', height:'250px'}} />
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo"style={{width:'250px', height:'250px'}}/>
            </div>
        </>
    );
};

export default Home;
