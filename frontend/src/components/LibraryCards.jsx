import React from 'react';

function LibraryCards({ bookInfo }) {
  return (
    <div>
      <div>{bookInfo.code}</div>
      <div>{bookInfo.name}</div>
      <div>{bookInfo.price}</div>
      <hr />
    </div>
  );
}

export default LibraryCards;