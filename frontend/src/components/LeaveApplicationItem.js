// src/components/LeaveApplicationItem.js
import React from 'react';

const LeaveApplicationItem = ({ application, onUpdateStatus }) => {
    const { id, employeeId, leaveType, start_date, end_date, reason, status } = application;

    const handleApprove = () => {
        onUpdateStatus(id, 1); // 1表示批准
    };

    const handleReject = () => {
        onUpdateStatus(id, 2); // 2表示拒绝
    };

    return (
        <tr>
            <td>{employeeId}</td>
            <td>{leaveType}</td>
            <td>{new Date(start_date).toLocaleDateString()}</td>
            <td>{new Date(end_date).toLocaleDateString()}</td>
            <td>{reason}</td>
            <td>{status === 0 ? 'Pending' : status === 1 ? 'Approved' : 'Rejected'}</td>
            <td>
                <button onClick={handleApprove}>Approve</button>
                <button onClick={handleReject}>Reject</button>
            </td>
        </tr>
    );
};

export default LeaveApplicationItem;
