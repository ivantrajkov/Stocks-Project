import React, {useState} from 'react';
import { Link } from "react-router-dom";
import './css/login.css';


const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const handleRegister = async () => {
        if (!username || !password) {
            alert("Please enter both username and password.");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/register?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to register');
            }

            const data = await response.json();
            if (data.redirect) {
                alert("Registration successful");
                window.location.href = data.redirect;
            }
        } catch (error) {
            alert(error.message);
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
                    <Link to="/login">Login</Link>
                    <Link to="/register" style={{ backgroundColor: '#d3d3d3' }}>Register</Link>
                </div>
            </nav>


            <div className="login">
                <label htmlFor={"text"}>Username:</label>
                <input type={"text"} id={"username"} onChange={(e) => setUsername(e.target.value)}/>
                <label htmlFor={"password"}>Password:</label>
                <input type={"password"} id={"password"} onChange={(e) => setPassword(e.target.value)}/>
                <input type={"button"} value={"Register"} onClick={handleRegister}/>
            </div>

            <div className="section">
                <img src="/resources/logo.png" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
                <img src="/resources/trust.jpg" alt="StocksAppMK Logo" style={{width: '250px', height: '250px'}}/>
            </div>
        </>
    );
};

export default Register;
