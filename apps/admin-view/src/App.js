import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AppLayout from './components/layout/AppLayout';
import Fare from './pages/Fares';
import Parking from './pages/Parking';
import Invoice from './pages/Invoice';
import Blank from './pages/Blank';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<AppLayout />}>
          <Route index element={<Blank />} />
          <Route path='/fares' element={<Fare />} />
          <Route path='/parking' element={<Parking />} />
          <Route path='/invoice' element={<Invoice />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
