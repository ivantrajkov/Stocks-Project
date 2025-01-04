import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Home from "./Pages/Home";
import List from "./List";
import StockMarket from "./Pages/stock-market";
import Login from "./Pages/Login";
import Graph from "./Pages/Graph";
import Analyze from "./Pages/Analyze";
import Register from "./Pages/Register";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home />} />
                <Route path='/List' exact={true} element={<List />} />

                <Route path='/stock-market' element={<StockMarket />} />
                <Route path='/Login' element={<Login />} />
                <Route path='/Graph' element={<Graph />} />
                <Route path={'Analyze'} element={<Analyze />} />
                <Route path={'Register'} element={<Register />} />
            </Routes>
        </Router>
    );
}

export default App;
