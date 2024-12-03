import React, {useState} from 'react';
import { Link } from "react-router-dom";
import './css/login.css';


const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const handleLogin = () => {
        if (!username || !password || username.length < 1 || password.length < 1) {
            alert("Please enter both username and password.");
        } else {
            alert("Logged in: " + username);
        }

    };
    return (
        <>
            <nav className="navbar">
                <img src="/resources/logo.png" alt="StocksAppMK Logo"/>
                <div>
                    <Link to="/">Home</Link>
                    <Link to="/stock-market">Stock market</Link>
                    <Link to="/analyze">Analyze</Link>
                    <Link to="/login" style={{ backgroundColor: '#d3d3d3' }}>Login</Link>
                </div>
            </nav>


            <div className="login">
                <label htmlFor={"text"}>Username:</label>
                <input type={"text"} id={"username"} onChange={(e) => setUsername(e.target.value)}/>
                <label htmlFor={"password"}>Password:</label>
                <input type={"password"} id={"password"} onChange={(e) => setPassword(e.target.value)}/>
                <input type={"button"} value={"Login"} onClick={handleLogin}/>
            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
            </div>
        </>
    );
};

export default Login;
