// src/components/LeaveApplicationList.js
import React from 'react';
import LeaveApplicationItem from './LeaveApplicationItem';

const LeaveApplicationList = ({ applications, onUpdateStatus }) => {
    return (
        <table>
            <thead>
            <tr>
                <th>Employee ID</th>
                <th>Leave Type</th>
                <th>From</th>
                <th>To</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {applications.map((application) => (
                <LeaveApplicationItem
                    key={application.id}
                    application={application}
                    onUpdateStatus={onUpdateStatus}
                />
            ))}
            </tbody>
        </table>
    );
};

export default LeaveApplicationList;
