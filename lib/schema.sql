-- =====================================
-- Drop ENUM types 
-- =====================================
DROP TYPE IF EXISTS copy_status;
DROP TYPE IF EXISTS fine_status;

-- =====================================
-- Create ENUM types used in system
-- =====================================
CREATE TYPE copy_status AS ENUM ('available','borrowed','lost');
CREATE TYPE fine_status AS ENUM ('unpaid','paid');

-- =====================================
-- Drop tables in correct order
-- =====================================
DROP TABLE IF EXISTS fine CASCADE;
DROP TABLE IF EXISTS borrowed_books CASCADE;
DROP TABLE IF EXISTS book_copy CASCADE;
DROP TABLE IF EXISTS book_author CASCADE;
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS publisher CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS librarian CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS userrole CASCADE;

-- =====================================
-- Table: userrole
-- =====================================
CREATE TABLE userrole (
    role_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- =====================================
-- Table: app_user
-- =====================================
CREATE TABLE app_user (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL REFERENCES userrole(role_id),
    phone VARCHAR(20),
    address TEXT
);

-- =====================================
-- Table: member
-- =====================================
CREATE TABLE member (
    member_id INT PRIMARY KEY REFERENCES app_user(user_id) ON DELETE CASCADE,
    join_date DATE NOT NULL
);

-- =====================================
-- Table: librarian
-- =====================================
CREATE TABLE librarian (
    librarian_id INT PRIMARY KEY REFERENCES app_user(user_id) ON DELETE CASCADE,
    hire_date DATE NOT NULL
);

-- =====================================
-- Table: author
-- =====================================
CREATE TABLE author (
    author_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT
);

-- =====================================
-- Table: publisher
-- =====================================
CREATE TABLE publisher (
    publisher_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT
);

-- =====================================
-- Table: category
-- =====================================
CREATE TABLE category (
    category_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- =====================================
-- Table: book
-- =====================================
CREATE TABLE book (
    book_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publisher_id INT REFERENCES publisher(publisher_id) ON DELETE SET NULL,
    category_id INT REFERENCES category(category_id) ON DELETE SET NULL,
    language VARCHAR(50),
    price DECIMAL(10,2)
);

-- =====================================
-- Table: book_author
-- =====================================
CREATE TABLE book_author (
    book_id INT REFERENCES book(book_id) ON DELETE CASCADE,
    author_id INT REFERENCES author(author_id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

-- =====================================
-- Table: book_copy
-- =====================================
CREATE TABLE book_copy (
    copy_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    book_id INT REFERENCES book(book_id) ON DELETE CASCADE,
    status copy_status DEFAULT 'available'
);

-- =====================================
-- Table: borrowed_books
-- =====================================
CREATE TABLE borrowed_books (
    borrowed_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    copy_id INT REFERENCES book_copy(copy_id) ON DELETE CASCADE,
    member_id INT REFERENCES member(member_id) ON DELETE CASCADE,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE
);

-- =====================================
-- Table: fine
-- =====================================
CREATE TABLE fine (
    fine_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    borrowed_id INT REFERENCES borrowed_books(borrowed_id) ON DELETE CASCADE,
    amount DECIMAL(10,2) NOT NULL,
    issued_date DATE NOT NULL,
    status fine_status DEFAULT 'unpaid'
);
