import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AppLayout from './components/layout/AppLayout';
import Book from './pages/Book';
import Blank from './pages/Blank';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<AppLayout />}>
          <Route index element={<Blank />} />
          <Route path='/books' element={<Book />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
