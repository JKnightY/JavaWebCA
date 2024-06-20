import React from 'react';

const LeaveApplicationItem = ({ application, onUpdateStatus }) => {
    const handleStatusChange = (newStatus) => {
        onUpdateStatus(application.id, newStatus);
    };

    return (
        <tr>
            <td>{application.id}</td>
            <td>{application.user ? application.user.username : '未知'}</td>
            <td>{application.leaveType ? application.leaveType.name : '未知'}</td>
            <td>{application.start_date}</td>
            <td>{application.end_date}</td>
            <td>{application.reason}</td>
            <td>{application.status}</td>
            <td>
                <button onClick={() => handleStatusChange('APPROVED')}>Approve</button>
                <button onClick={() => handleStatusChange('REJECTED')}>Reject</button>
            </td>
        </tr>
    );
};

export default LeaveApplicationItem;
