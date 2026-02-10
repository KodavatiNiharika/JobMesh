import React, {use, useEffect, useState} from "react";
import "./Signup.css"
import axios from "axios";
import { Link } from "react-router-dom";

import {useNavigate} from "react-router-dom"; 
function Signup() {
    const [userName, setUsername] = useState("");
    const [userEmail, setUserEmail] = useState("");
    const [ReTypedPassword , setReTypedPassword] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();
    useEffect(() => {
        const token = localStorage.getItem("token");
        if(token) {
            navigate("/dashboard");
        }
    }, [navigate]);
    const handleSubmit = async(e) => {
        e.preventDefault();
        if(password !== ReTypedPassword) {
            setError("Password mis-match");
            return;
        }
        if(password === "" || userName === "" || userEmail === "") {
            setError("Enter credentials");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/users/signup", {
                userName,
                email: userEmail,
                password
            });
            if(response.data.token) {
                const token = response.data.token;
                localStorage.setItem("token", token);
                console.log("User created:", response.data);
                alert("Signup successful!");
                navigate("/dashboard");
            } else if(response.data.error) {
                setError(response.data.error);
            } else {
                setError("Signup failed");
            }
        } catch(err) {
            console.log(err);
            setError("Signup failed");
        }
    };
    return (
        <>
        <div className="signup-form">
            <h1> Signup page </h1>
            <form className="signup-form" onSubmit={handleSubmit}>
                <input type="text" className="userName" placeholder="Enter username" onChange={(e) => setUsername(e.target.value)}></input>
                <input type="text" className="userEmail" placeholder="Enter Email" onChange={(e) => setUserEmail(e.target.value)}></input>
                <input type="password" className="password" placeholder="Enter password" onChange={(e) => setPassword(e.target.value)}></input>
                <input type="password" className="retype-password" placeholder="Re-Type Password" onChange={(e) => setReTypedPassword(e.target.value)}></input>
                
                <button type="submit" className="signup-button">Signup</button>
                <p> Already have an account?</p>
                <Link to='/login'>Login</Link>
            </form>
            {error && <p style={{color:"white"}}>{error}</p>}
        </div>
        </>
    );
}
export default Signup;