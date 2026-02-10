import React, { useState } from "react";
import "./Profile.css";
import Navbar from "../../components/Navbar/Navbar";
import axios from "axios";
const Profile = () => {
  const user = {
    userName: "Niharika",
    email: "niharika@gmail.com",
  };
    const [resume, setResume] = useState(null);
    const handleResumeUpload = async () => {
  if (!resume) {
    alert("Please select a file first!");
    return;
  }

  const formData = new FormData();
  formData.append("file", resume);

  const userId = localStorage.getItem("userId");
  formData.append("userId", userId);
  console.log(userId);

  try {
    const token = localStorage.getItem("token");
    console.log(token);

const response = await axios.post(
  "http://localhost:8080/api/resume/upload",
  formData,
  {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "multipart/form-data",
    },
  }
);


    alert("Resume uploaded successfully!");
    console.log(response.data);

  } catch (error) {
    console.log(error);
    alert("Upload failed!");
  }
};



  return (
    <>
      <Navbar />

      <div className="profile-page">

        {/* ===== TOP HEADER ===== */}
        <div className="profile-top">
          <div className="profile-identity">
            <div className="avatar-lg">N</div>
            <div>
              <h1>{user.userName}</h1>
              <p>{user.email}</p>
            </div>
          </div>

          <button className="btn outline">Edit Profile</button>
        </div>

        {/* ===== MAIN STACK (ONE CARD PER ROW) ===== */}
        <div className="profile-stack">

          {/* ===== RESUME CARD ===== */}
          <div className="panel-card wide horizontal-card">

            <div className="card-left">
                <input 
                    type="file"
                    accept=".pdf,.doc,.docx"
                    onChange={(e) => setResume(e.target.files[0])}
                    />
                    <button className="btn primary" onClick={handleResumeUpload}>
                    Upload Resume
                    </button>
            </div>

            <div className="card-right">
              <button className="btn primary" onClick={handleResumeUpload}>Upload</button>
              <button className="btn secondary">View</button>
              <button className="btn outline">Replace</button>
              <button className="btn danger">Delete</button>
            </div>
          </div>

          {/* ===== SELECTED COMPANIES ===== */}
          <div className="panel-card wide horizontal-card">

            <div className="card-left">
              <h3>Selected Companies</h3>
              <div className="chip-container">
                <span className="chip">Google</span>
                <span className="chip">Microsoft</span>
                <span className="chip">Amazon</span>
                <span className="chip">Infosys</span>
                <span className="chip">TCS</span>
              </div>
            </div>

            <div className="card-right">
              <button className="btn secondary">Manage</button>
            </div>
          </div>

          {/* ===== ATS PREFERENCE ===== */}
          <div className="panel-card wide horizontal-card">

            <div className="card-left">
              <h3>ATS Preference</h3>
              <p className="small-text">Minimum ATS Score</p>
              <span className="status highlight">70%</span>
            </div>

            <div className="card-right">
              <button className="btn primary">Change Threshold</button>
            </div>
          </div>

          {/* ===== EMAIL NOTIFICATIONS ===== */}
          <div className="panel-card wide horizontal-card">

            <div className="card-left">
              <h3>Email Notifications</h3>
              <span className="status on">Job Match Alerts: ON</span>
            </div>

            <div className="card-right">
              <button className="btn secondary">Toggle</button>
            </div>
          </div>

          {/* ===== LOGOUT ===== */}
          <button className="btn danger full logout-btn">Logout</button>

        </div>
      </div>
    </>
  );
};

export default Profile;
