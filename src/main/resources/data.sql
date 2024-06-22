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
VALUES (1, 14, 'AnnualLeave'),
       (2, 60, 'MedicalLeave'),
       (3, 100, 'CompensationLeave')
ON DUPLICATE KEY UPDATE maxdays=VALUES(maxdays),
                        name=VALUES(name);
