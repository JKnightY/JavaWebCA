// src/App.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import LeaveApplicationList from './components/LeaveApplicationList';
import StatusFilter from './components/StatusFilter';
import './App.css';

function App() {
    const [leaveApplications, setLeaveApplications] = useState([]);
    const [status, setStatus] = useState(0); // 默认显示待处理的申请
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchLeaveApplications = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/leaveapplications/status/${status}`);
                if (Array.isArray(response.data)) {
                    setLeaveApplications(response.data);
                } else {
                    console.error('Expected an array but got:', response.data);
                    setError('Unexpected data format');
                }
            } catch (error) {
                setError('Error fetching leave applications');
                console.error('Error fetching leave applications:', error);
            }
        };

        fetchLeaveApplications();
    }, [status]);

    const updateLeaveApplicationStatus = async (id, newStatus) => {
        try {
            await axios.put(`http://localhost:8080/leaveapplications/${id}/status`, { status: newStatus });
            // 更新后重新获取数据
            const response = await axios.get(`http://localhost:8080/leaveapplications/status/${status}`);
            setLeaveApplications(response.data);
        } catch (error) {
            setError('Error updating leave application status');
            console.error('Error updating leave application status:', error);
        }
    };

    return (
        <div className="App">
            <div className="sidebar">
                <h2>Menu</h2>
                <ul>
                    <li><a href="#home">Home</a></li>
                    <li><a href="#applications">Leave Applications</a></li>
                    <li><a href="#settings">Settings</a></li>
                </ul>
            </div>
            <div className="main-content">
                <h1>Leave Applications Management</h1>
                <StatusFilter status={status} setStatus={setStatus} />
                {error && <p>{error}</p>}
                <LeaveApplicationList applications={leaveApplications} onUpdateStatus={updateLeaveApplicationStatus} />
            </div>
        </div>
    );
}

export default App;
