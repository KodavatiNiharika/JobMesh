import { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "../components/Navbar/Navbar";

function Dashboard() {
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");

    axios.get("http://localhost:8080/dashboard", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(res => setJobs(res.data))
    .catch(err => console.error(err));
  }, []);

  return (
    <div>
      <Navbar />
      <h2>Jobs With ATS Score &gt; 40</h2>

      {jobs.map((job) => (
        <div key={job.id} style={{
          border: "1px solid #ccc",
          padding: "15px",
          marginBottom: "10px",
          borderRadius: "8px"
        }}>
          <h3>{job.title}</h3>
          <p><strong>Company:</strong> {job.company}</p>
          <p><strong>Location:</strong> {job.location}</p>
          <p>{job.description}</p>
        </div>
      ))}
    </div>
  );
}

export default Dashboard;
