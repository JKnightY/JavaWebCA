-- Initialize public_holiday data
INSERT INTO public_holiday (id, description, holiday_date)
VALUES (1, 'New Year''s Day', '2024-01-01'),
       (2, 'Chinese New Year', '2024-02-10'),
       (3, 'Good Friday', '2024-04-07'),
       (4, 'Labour Day', '2024-05-01'),
       (5, 'Hari Raya Puasa', '2024-06-29'),
       (6, 'Hari Raya Haji', '2024-06-28'),
       (7, 'National Day', '2024-08-09'),
       (8, 'Deepavali', '2024-11-12'),
       (9, 'Christmas Day', '2024-12-25')
ON DUPLICATE KEY UPDATE description=VALUES(description),
                        holiday_date=VALUES(holiday_date);

-- Initialize leave_type data
INSERT INTO leave_type (id, maxdays, name)
VALUES (1, 14, 'Annual Leave (administrative employees)'),
       (2, 18, 'Annual Leave (professional employees)'),
       (3, 60, 'Medical Leave'),
       (4, 100, 'Compensation Leave')
ON DUPLICATE KEY UPDATE maxdays=VALUES(maxdays),
                        name=VALUES(name);

-- Initialize users data with an admin account
INSERT INTO users (id, account, password, username, department, role, email, leave_approverid, annual_leave_entitlement, annual_leave_entitlement_last, medical_leave_entitlement, medical_leave_entitlement_last, compensation_leave_balance, compensation_leave_balance_last) VALUES
    (1, 'admin', 'admin', 'admin', 0, 0, 'admin@example.com', NULL, 0, 0, 0, 0, 0, 0)
ON DUPLICATE KEY UPDATE account=VALUES(account), password=VALUES(password), role=VALUES(role), username=VALUES(username);
