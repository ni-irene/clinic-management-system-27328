
import React from 'react';
import { Link } from 'react-router-dom';

function LibraryHeader() {
  return (
    <header>
      <h1>LIBRARY MANAGEMENT SYSTEM</h1>

      <nav>
        <Link to="/books">BOOKS</Link>
        <Link to="/office">OFFICE</Link>
        <Link to="/">LOGOUT</Link>
      </nav>
    </header>
  );
}

export default LibraryHeader;
