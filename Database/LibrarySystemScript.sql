CREATE DATABASE library_management_system;

USE library_management_system;

CREATE TABLE Book (
	book_id INT auto_increment primary key,
    title varchar(255) not null,
    author VARCHAR(255) NOT NULL,
    publication_year INT,
    isbn VARCHAR(13) UNIQUE NOT NULL,
    edition varchar(50),
    description varchar(255),
    is_available boolean
);

CREATE TABLE Patron (
    patron_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_information VARCHAR(255)
);

CREATE TABLE Borrowing_Record (
    borrowing_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    patron_id INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES Book(book_id),
    FOREIGN KEY (patron_id) REFERENCES Patron(patron_id)
);
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `role` enum('Admin','User') NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci