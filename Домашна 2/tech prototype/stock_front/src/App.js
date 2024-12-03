import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Home from "./Home";
import List from "./List";
import StockMarket from "./stock-market";
import Login from "./Login";
import Graph from "./Graph";
import Analyze from "./Analyze";

function App() {
  return (
      <Router>
        <Routes>
          {/* Existing routes */}
          <Route exact path="/" element={<Home />} />
          <Route path='/List' exact={true} element={<List />} />

          {/* Add route for /stock-market */}
          <Route path='/stock-market' element={<StockMarket />} />
          <Route path='/Login' element={<Login />} />
          <Route path='/Graph' element={<Graph />} />
          <Route path={'Analyze'} element={<Analyze />} />
        </Routes>
      </Router>
  );
}

export default App;