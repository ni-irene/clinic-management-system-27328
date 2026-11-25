
import React from 'react';
import LibraryCards from './LibraryCards';

function LibraryBooks() {
    const books = [
        {
            code: "WEB 001",
            name: "Full stack development",
            price: 120
        },
        {
            code: "SOE",
            name: "Software development",
            price: 100
        },
        {
            code: "SOD 001",
            name: "Software engineering",
            price: 130
        }
    ];

    const totalBooks = books.length;
    const totalPrice = books.reduce((total, book) => total + book.price, 0);

    return (
        <div>
            <h2>List of Books</h2>

            <ul>
                {books.map(book => (
                    <LibraryCards key={book.code} bookInfo={book} />
                ))}
            </ul>

            <p>Total number of books: {totalBooks}</p>
            <p>Total price: {totalPrice}</p>
        </div>
    );
}

export default LibraryBooks;
