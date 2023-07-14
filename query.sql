CREATE DATABASE budget COLLATE utf8mb4_polish_ci;

CREATE TABLE budget.transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type ENUM('Expenditure', 'Income') NOT NULL,
    description VARCHAR(2000) NOT NULL,
    amount DECIMAL(10 , 2 ) NOT NULL,
    date DATETIME NOT NULL
);

INSERT INTO
	budget.transaction (type, description, amount, date)
VALUES
	('Expenditure', 'Zakupy spożywcze', 349.54, '2023-02-11 10:53:00'),
    ('Income', 'Wypłata z tytułu umowy o pracę', 6055.00, '2023-02-10 18:00:00'),
    ('Income', 'Zwrot podatku', 3000.00, '2023-01-25 11:30:00'),
    ('Expenditure', 'Paliwo', 659.25, '2021-12-03 14:25:00');	