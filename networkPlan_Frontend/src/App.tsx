import React from 'react';
import logo from './logo.svg';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import './App.css';
import DisplayIpAddresses from "./components/DisplayIpAddresses";
import SubmitForm from "./components/SubmitForm";
import SearchForm from "./components/SearchForm";

export interface IState {
    addresses: {
        ip: string
        mask: string
        amount: number
    }[]
}

function App() {
    document.title = 'äosTECH'
  return (
      <BrowserRouter>
        <Routes>
            <Route path="/" element={<DisplayIpAddresses/> } />
            <Route path="/form" element={<SubmitForm/> } />
            <Route path="/search" element={<SearchForm/>}/>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
