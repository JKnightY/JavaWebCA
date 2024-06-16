import React from 'react';

const StatusFilter = ({ status, setStatus }) => {
    return (
        <div>
            <label>
                Filter by Status:
                <select value={status} onChange={(e) => setStatus(parseInt(e.target.value, 10))}>
                    <option value={0}>Pending</option>
                    <option value={1}>Approved</option>
                    <option value={2}>Rejected</option>
                </select>
            </label>
        </div>
    );
};

export default StatusFilter;
