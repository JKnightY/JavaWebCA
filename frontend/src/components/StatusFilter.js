import React from 'react';

const StatusFilter = ({ status, setStatus }) => {
    return (
        <div>
            <label>
                Filter by Status:
                <select value={status} onChange={(e) => setStatus(e.target.value)}>
                    <option value="APPLIED">待处理</option>
                    <option value="APPROVED">已批准</option>
                    <option value="REJECTED">已拒绝</option>
                    <option value="CANCEL">取消</option>
                    <option value="UPDATED">更新</option>
                    <option value="DELETED">删除</option>
                </select>
            </label>
        </div>
    );
};

export default StatusFilter;
