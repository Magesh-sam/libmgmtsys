-- =====================================
-- Insert Data: user roles
-- =====================================
INSERT INTO userrole (name) VALUES 
('member'),
('librarian'),
('admin');

-- =====================================
-- Insert Data: app users (3 members, 2 librarians, 1 admin)
-- =====================================
INSERT INTO app_user (name, email, password, role_id, phone, address) VALUES
('Alice Johnson', 'alice@example.com', 'pass1234', 1, '9876543210', 'NY, USA'),
('Bob Smith', 'bob@example.com', 'pass1234', 1, '9123456780', 'LA, USA'),
('Charlie Brown', 'charlie@example.com', 'pass1234', 1, '9988776655', 'Chicago, USA'),
('David Miller', 'david@example.com', 'pass1234', 2, '9090909090', 'Boston, USA'),
('Eve Turner', 'eve@example.com', 'pass1234', 2, '9191919191', 'Seattle, USA'),
('Admin User', 'admin@example.com', 'admin123', 3, '9000000000', 'Head Office');

-- =====================================
-- Insert Data: members and librarians
-- =====================================
INSERT INTO member (member_id, join_date) VALUES
(1, '2023-01-10'),
(2, '2023-02-15'),
(3, '2023-03-20');

INSERT INTO librarian (librarian_id, hire_date) VALUES
(4, '2022-05-12'),
(5, '2022-06-18');

-- =====================================
-- Insert Data: authors
-- =====================================
INSERT INTO author (name, bio) VALUES
('J.K. Rowling', 'British author, Harry Potter series'),
('George Orwell', 'Author of dystopian novels'),
('J.R.R. Tolkien', 'Author of Lord of the Rings');

-- =====================================
-- Insert Data: publishers
-- =====================================
INSERT INTO publisher (name, address) VALUES
('Penguin Books', 'London, UK'),
('HarperCollins', 'New York, USA');

-- =====================================
-- Insert Data: categories
-- =====================================
INSERT INTO category (name, description) VALUES
('Fantasy', 'Magical fictional works'),
('Dystopian', 'Dark futuristic worlds'),
('Adventure', 'Exciting journey-based stories');

-- =====================================
-- Insert Data: books
-- =====================================
INSERT INTO book (title, publisher_id, category_id, language, price) VALUES
('Harry Potter and the Sorcerer''s Stone', 1, 1, 'English', 19.99),
('1984', 2, 2, 'English', 14.50),
('The Hobbit', 1, 3, 'English', 21.00);

-- =====================================
-- Insert Data: book-author relationships
-- =====================================
INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- =====================================
-- Insert Data: book copies
-- =====================================
INSERT INTO book_copy (book_id, status) VALUES
(1, 'available'),
(1, 'borrowed'),
(2, 'available'),
(3, 'borrowed'),
(3, 'available');

-- =====================================
-- Insert Data: borrow records
-- =====================================
INSERT INTO borrowed_books (copy_id, member_id, borrow_date, due_date, return_date) VALUES
(2, 1, '2024-01-01', '2024-01-15', NULL),
(4, 2, '2024-01-05', '2024-01-20', '2024-01-18'),
(3, 3, '2024-01-10', '2024-01-25', NULL);

-- =====================================
-- Insert Data: fines
-- =====================================
INSERT INTO fine (borrowed_id, amount, issued_date, status) VALUES
(1, 10.00, '2024-01-20', 'unpaid'),
(2, 0.00, '2024-01-19', 'paid'),
(3, 5.00, '2024-01-28', 'unpaid');