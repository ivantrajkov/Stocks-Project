import React, { useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import './css/login.css';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [responseInfo, setResponseInfo] = useState(null);
    const navigate = useNavigate();

    const handleLogin = () => {
        if (!username || !password) {
            alert("Please enter both username and password.");
            return;
        }

        // Basic Fetch API usage
        fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ username, password })
        }).then((response) => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Invalid login credentials');
            }
        })
            .then((data) => {
                setResponseInfo("Login successful!");
                navigate('/');
            })
            .catch((error) => {
                setResponseInfo(`Error: ${error.message}`);
            });
    };

    return (
        <>
            <nav className="navbar">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" />
                <div>
                    <Link to="/">Home</Link>
                    <Link to="/stock-market">Stock market</Link>
                    <Link to="/analyze">Analyze</Link>
                    <Link to="/login" style={{ backgroundColor: '#d3d3d3' }}>Login</Link>
                    <Link to="/register">Register</Link>
                </div>
            </nav>

            <div className="login">
                <label htmlFor={"username"}>Username:</label>
                <input
                    type={"text"}
                    id={"username"}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <label htmlFor={"password"}>Password:</label>
                <input
                    type={"password"}
                    id={"password"}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <input
                    type={"button"}
                    value={"Login"}
                    onClick={handleLogin}
                />
                {responseInfo && (
                    <div className="response-info">
                        <pre>{responseInfo}</pre>
                    </div>
                )}
            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{ width: '250px', height: '250px' }} />
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{ width: '250px', height: '250px' }} />
            </div>
        </>
    );
};

export default Login;
