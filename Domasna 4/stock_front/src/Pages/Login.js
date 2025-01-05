import React, { useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import '../css/login.css';
import NavBar from "../components/NavBar/NavBar";
import Footer from "../components/Footer/Footer";

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

        fetch('http://stocks-app:8080/authentication/login', {
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
            <NavBar/>

            <div className="login">
                <h1>Login:</h1>
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

            <Footer/>
        </>
    );
};

export default Login;
