import React, { useState } from "react";
import axios from "axios";
import "./UploadJob.css";
import Navbar from "../../components/Navbar/Navbar";

const UploadJob = () => {
  const [job, setJob] = useState({
    title: "",
    description: "",
    company: "",
    location: "",
    applyLink: ""
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const userId = localStorage.getItem("userId");
  console.log(userId);

  const handleChange = (e) => {
    setJob({
      ...job,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const token = localStorage.getItem("token");

      await axios.post(
        "http://localhost:8080/jobs/create",
        job,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );


      setMessage("Job uploaded successfully!");
      setJob({
        title: "",
        description: "",
        company: "",
        location: "",
        applyLink: ""
      });

    } catch (error) {
      console.error(error);
      setMessage("Failed to upload job.");
    }

    setLoading(false);
  };
return (
    <>
    <Navbar/>
  <div className="uploadContainer">
    <div className="uploadCard">
      <h2 className="uploadTitle">Upload Job</h2>

      <form onSubmit={handleSubmit} className="uploadForm">


        <input
          type="text"
          name="title"
          placeholder="Job Title"
          value={job.title}
          onChange={handleChange}
          required
          className="uploadInput"
        />

        <textarea
          name="description"
          placeholder="Job Description"
          value={job.description}
          onChange={handleChange}
          required
          className="uploadTextarea"
        />

        <input
          type="text"
          name="company"
          placeholder="Company"
          value={job.company}
          onChange={handleChange}
          required
          className="uploadInput"
        />

        <input
          type="text"
          name="location"
          placeholder="Location"
          value={job.location}
          onChange={handleChange}
          required
          className="uploadInput"
        />

        <input
          type="url"
          name="applyLink"
          placeholder="Official Apply Link (https://...)"
          value={job.applyLink}
          onChange={handleChange}
          required
          className="uploadInput"
        />

        <button type="submit" disabled={loading} className="uploadButton">
          {loading ? "Uploading..." : "Upload Job"}
        </button>
      </form>

      {message && <p className="uploadMessage">{message}</p>}
    </div>
  </div>
  </>
);
};
export default UploadJob;