import {jwtDecode} from "jwt-decode";
import {useState, useEffect } from "react";
import Navbar from "../components/Navbar/Navbar";
function Dashboard() {
    const [email, setEmail] = useState("");
    useEffect(() => {
        const token = localStorage.getItem("token");
        if(token) {
            try {
                const decoded = jwtDecode(token);
                console.log(decoded);
                setEmail(decoded.sub);
            } catch(err) {
                console.log("error :" + err);
            }
        }
    }, []);
    
    return (
        <>
        <Navbar/>
        <h1>Dashboard</h1>
        <h2>{email}</h2>
        </>
    );
};
export default Dashboard;