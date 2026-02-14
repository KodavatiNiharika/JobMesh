import React from "react";
import { useNavigate, Link } from "react-router-dom";
import "./Navbar.css";
function Navbar() {
    const navigate = useNavigate();
    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("userId");
        navigate("/");
    };
    return (
        <nav className="navBar">
            <Link to="/" className="NavBarLinks"> Home </Link>
            <Link to="/jobs" className="NavBarLinks"> Jobs </Link>
            <Link to="/profile" className="NavBarLinks"> Profile </Link>
            <button onClick={handleLogout} className="Logout"> Logout </button>
        </nav>
    );
}
export default Navbar;