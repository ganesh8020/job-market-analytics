import React, { useEffect, useState } from "react";
import "./App.css";
import { Bar, Pie } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

function App() {
  const [jobs, setJobs] = useState([]);
  const [skills, setSkills] = useState({});
  const [avgSalary, setAvgSalary] = useState(0);

  useEffect(() => {
    fetch("http://localhost:8080/jobs")
      .then((res) => res.json())
      .then((data) => {
        setJobs(data);

        // Average Salary
        const total = data.reduce((sum, j) => sum + j.salary, 0);
        setAvgSalary((total / data.length).toFixed(0));

        // Skill Count
        const skillMap = {};
        data.forEach((j) => {
          skillMap[j.skill] = (skillMap[j.skill] || 0) + 1;
        });
        setSkills(skillMap);
      });
  }, []);

  // Colors
  const colors = [
    "#3498db",
    "#e74c3c",
    "#2ecc71",
    "#f1c40f",
    "#9b59b6",
    "#1abc9c",
  ];

  // Bar Chart Data
  const barData = {
    labels: Object.keys(skills),
    datasets: [
      {
        label: "Skill Demand",
        data: Object.values(skills),
        backgroundColor: colors,
        borderRadius: 6,
      },
    ],
  };

  // Pie Chart Data
  const pieData = {
    labels: Object.keys(skills),
    datasets: [
      {
        data: Object.values(skills),
        backgroundColor: colors,
      },
    ],
  };

  // Chart Options
  const options = {
    plugins: {
      legend: {
        position: "top",
      },
    },
  };

  return (
    <div className="container">
      <h1>Job Market Analytics Dashboard</h1>

      {/* Salary Card */}
      <div className="card">
        <h2>Average Salary</h2>
        <h3>${avgSalary}</h3>
      </div>

      {/* Bar Chart */}
      <div className="card">
        <h2>Top Skills (Bar Chart)</h2>
        <Bar data={barData} options={options} />
      </div>

      {/* Pie Chart */}
      <div className="card">
        <h2>Skill Distribution</h2>
        <Pie data={pieData} options={options} />
      </div>

      {/* Jobs List */}
      <div className="card">
        <h2>Jobs</h2>
        <ul>
          {jobs.map((job) => (
            <li key={job.id}>
              <b>{job.title}</b> - {job.company} ({job.location})
              {job.salary}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App;