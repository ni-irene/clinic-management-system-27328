import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css'
import LibraryButton from './components/LibraryButton'
import LibraryBooks from './components/LibraryBooks'
import LibraryFooter from './components/LibraryFooter'
import LibraryHeader from './components/LibraryHeader'
import LibraryLogin from './components/LibraryLogin'
import Office from './components/Office'

function App() {


   return (
    <BrowserRouter>
      <LibraryHeader />

      <Routes>
        <Route path="/" element={<LibraryLogin />} />
        <Route path="/books" element={<LibraryBooks />} />
        <Route path="/office" element={<Office />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App
