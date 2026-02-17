import React, { useState } from "react";
import "./Profile.css";
import { createPortal } from "react-dom";
import Navbar from "../../components/Navbar/Navbar";
import axios from "axios";
import { useEffect } from "react";
import { useRef } from "react";

const Profile = () => {
  const [resumeUrl, setResumeUrl] = useState("");
  const [resumeId, setResumeId] = useState(null);
  const [isResumeOpen, setIsResumeOpen] = useState(false);
  const [resumeName, setResumeName] = useState("");
  const [userJobs, setUserJobs] = useState([]);

  const fileInputRef = useRef(null);



  const user = {
    userName: "Niharika",
    email: "niharika@gmail.com",
  };
    
   useEffect(() => {
  const fetchResume = async () => {
    try {
      const userId = localStorage.getItem("userId");
      const token = localStorage.getItem("token");
      console.log(userId);
      console.log(token);
      const response = await axios.get(
        `http://localhost:8080/api/resume/user/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setResumeId(response.data.id);
      setResumeName(response.data.fileName);

    } catch (error) {
      console.log("No resume found or unauthorized");
    }
  };

  fetchResume();
}, []);



    const handleResumeUpload = () => {
        fileInputRef.current.click();
      };
      
      const handleFileChange = async (e) => {
    const selectedFile = e.target.files[0];
    if (!selectedFile) return;

    const formData = new FormData();
    formData.append("file", selectedFile);
    formData.append("userId", localStorage.getItem("userId"));

    try {
      const token = localStorage.getItem("token");

      const response = await axios.post(
        "http://localhost:8080/api/resume/upload",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const savedResume = response.data;

      setResumeId(savedResume.id);
      setResumeName(savedResume.fileName);

      alert("Resume uploaded successfully!");

    } catch (error) {
      console.error(error);
      alert("Upload failed!");
    }
  };



const handleViewResume = async (resumeId) => {
  if (!resumeId) {
    alert("No resume uploaded yet!");
    return;
  }

  try {
    const token = localStorage.getItem("token");

    const response = await axios.get(
      `http://localhost:8080/api/resume/view/${resumeId}`,
      {
        responseType: "blob",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    const file = new Blob([response.data], { type: "application/pdf" });
    const fileURL = URL.createObjectURL(file);

    setResumeUrl(fileURL);
    setIsResumeOpen(true);
  } catch (error) {
    console.error(error);
    alert("Failed to load resume");
  }
};
 
  const handleDelete = async () => {
  if (!resumeId) {
    alert("No resume to delete!");
    return;
  }

  const confirmDelete = window.confirm(
    "Are you sure you want to delete your resume?"
  );
  if (!confirmDelete) return;

  try {
    const token = localStorage.getItem("token");

    await axios.delete(
      `http://localhost:8080/api/resume/${resumeId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    // Clear frontend state
    setResumeId(null);
    setResumeName("");
    setResumeUrl("");
    setIsResumeOpen(false);

    alert("Resume deleted successfully!");

  } catch (error) {
    console.error(error);
    alert("Failed to delete resume!");
  }
};


  const handleCloseResume = () => {
    setIsResumeOpen(false);
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

            <div className="card-left resume-left">
              <input
                type="file"
                accept=".pdf,.doc,.docx"
                ref={fileInputRef}
                onChange={handleFileChange}
                style={{ display: "none" }}
              />

              {resumeId && (
                <div className="resume-display">
                  <span className="resume-icon">📄</span>
                  <span className="resume-text">{resumeName}</span>
                </div>
              )}
              </div>


            <div className="card-right">
              <button className="btn primary" onClick={handleResumeUpload}>
                {resumeId ? "Upload New Version" : "Upload"}
              </button>
              {resumeId && (
                 <>
              <button className="btn secondary" onClick={() => handleViewResume(resumeId)} disabled={!resumeId}>View</button>
               {isResumeOpen &&
                  createPortal(
                    <div className="resume-modal">
                      <button className="close-btn" onClick={handleCloseResume}>
                        ×
                      </button>

                      <iframe
                        src={resumeUrl}
                        title="Resume"
                        className="resume-frame"
                      />
                    </div>,
                    document.body
                  )
                }
              <button className="btn danger" onClick={handleDelete} disabled={!resumeId} >Delete</button>
              </>
                 )}
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