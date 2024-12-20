import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Home from "./Home";
import List from "./List";
import StockMarket from "./stock-market";
import Login from "./Login";
import Graph from "./Graph";
import Analyze from "./Analyze";
import Register from "./Register";

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
