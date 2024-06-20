import React from 'react';
import LeaveApplicationItem from './LeaveApplicationItem';

const LeaveApplicationList = ({ applications, onUpdateStatus }) => {
    return (
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>User</th>
                <th>Leave Type</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {applications.map(application => (
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
