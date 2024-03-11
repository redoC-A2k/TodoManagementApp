import { Route, Routes, useNavigate } from 'react-router-dom';
import Home from './screens/Home';
import Signin from './screens/Signin';
import Signup from './screens/Signup';
import Todo from './screens/Todo';
import { useEffect } from 'react';

function App() {
  const navigate = useNavigate()

  useEffect(()=>{
    let token = localStorage.getItem('token');
    if(!token)
    navigate('/signin')
  },[])
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/signin" element={<Signin />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/todo/:id" element={<Todo />} />
    </Routes>
  );
}

export default App;
