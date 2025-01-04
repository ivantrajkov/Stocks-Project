import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar">
            <img src="/resources/logo.png" alt="StocksAppMK Logo" />
            <div>
                <Link to="/" className={"home"}>Home</Link>
                <Link to="/stock-market" className={"stock-market"}>Stock market</Link>
                <Link to="/Analyze" className={"analyze"}>Analyze</Link>
                <Link to="/Login" className={"analyze"}>Login</Link>
                <Link to="/Register" className={"register"}>Register</Link>
            </div>
        </nav>
    );
};

export default Navbar;