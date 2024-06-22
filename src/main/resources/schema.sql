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

CREATE TABLE IF NOT EXISTS users
(
    id                              INT AUTO_INCREMENT PRIMARY KEY,
    account                         VARCHAR(255) NOT NULL,
    password                        VARCHAR(255) NOT NULL,
    username                        VARCHAR(255),
    department                      INT,
    role                            INT,
    email                           VARCHAR(255),
    leave_approverid                INT,
    annual_leave_entitlement        INT,
    annual_leave_entitlement_last   INT,
    medical_leave_entitlement       INT,
    medical_leave_entitlement_last  INT,
    compensation_leave_balance      INT,
    compensation_leave_balance_last INT
);