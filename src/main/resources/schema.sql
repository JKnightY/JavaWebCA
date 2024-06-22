CREATE TABLE IF NOT EXISTS public_holiday
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    description  VARCHAR(255) NOT NULL,
    holiday_date DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS leave_type
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    maxdays INT          NOT NULL,
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS leave_application
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    contact_details    VARCHAR(255),
    created_at         TIMESTAMP,
    end_date           DATE NOT NULL,
    reason             VARCHAR(255),
    start_date         DATE NOT NULL,
    status             VARCHAR(255),
    updated_at         TIMESTAMP,
    work_dissemination VARCHAR(255),
    leave_type_id      INT,
    user_id            INT,
    FOREIGN KEY (leave_type_id) REFERENCES leave_type (id)
);
