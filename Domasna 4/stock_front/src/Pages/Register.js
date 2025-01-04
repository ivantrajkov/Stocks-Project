import React, {useState} from 'react';
import { Link } from "react-router-dom";
import '../css/login.css';
import NavBar from "../components/NavBar/NavBar";
import Footer from "../components/Footer/Footer";


const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const handleRegister = async () => {
        if (!username || !password) {
            alert("Please enter both username and password.");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/authentication/register?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`, {
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
            <NavBar/>


            <div className="login">
                <h1>Register:</h1>
                <label htmlFor={"text"}>Username:</label>
                <input type={"text"} id={"username"} onChange={(e) => setUsername(e.target.value)}/>
                <label htmlFor={"password"}>Password:</label>
                <input type={"password"} id={"password"} onChange={(e) => setPassword(e.target.value)}/>
                <input type={"button"} value={"Register"} onClick={handleRegister}/>
            </div>

            <Footer/>
        </>
    );
};

export default Register;
