import React, { useEffect, useState } from 'react';
import axios from 'axios';
import LeaveApplicationList from './components/LeaveApplicationList';
import StatusFilter from './components/StatusFilter';
import './App.css';

function App() {
    const [leaveApplications, setLeaveApplications] = useState([]);
    const [status, setStatus] = useState('APPLIED'); // 默认显示待处理的申请
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchLeaveApplications = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/manager/status/${status}`);
                if (Array.isArray(response.data)) {
                    setLeaveApplications(response.data);
                } else {
                    console.error('预期为数组，但得到:', response.data);
                    setError('数据格式不正确');
                }
            } catch (error) {
                setError('获取请假申请时出错');
                console.error('获取请假申请时出错:', error);
            }
        };

        fetchLeaveApplications();
    }, [status]);

    const updateLeaveApplicationStatus = async (id, newStatus) => {
        try {
            const updatedApplication = { status: newStatus }; // 创建一个包含新状态的对象
            await axios.put(`http://localhost:8080/manager/${id}/status`, updatedApplication);
            // 更新后重新获取数据
            const response = await axios.get(`http://localhost:8080/manager/status/${status}`);
            setLeaveApplications(response.data);
        } catch (error) {
            setError('更新请假申请状态时出错');
            console.error('更新请假申请状态时出错:', error);
        }
    };

    return (
        <div className="App">
            <div className="sidebar">
                <h2>菜单</h2>
                <ul>
                    <li><a href="#home">主页</a></li>
                    <li><a href="#applications">请假申请</a></li>
                    <li><a href="#settings">设置</a></li>
                </ul>
            </div>
            <div className="main-content">
                <h1>请假申请管理</h1>
                <StatusFilter status={status} setStatus={setStatus} />
                {error && <p>{error}</p>}
                <LeaveApplicationList applications={leaveApplications} onUpdateStatus={updateLeaveApplicationStatus} />
            </div>
        </div>
    );
}

export default App;
