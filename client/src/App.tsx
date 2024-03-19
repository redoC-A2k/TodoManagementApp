import { Route, Routes, useNavigate} from 'react-router-dom';
import Home from './screens/Home';
import Signin from './screens/Signin';
import Signup from './screens/Signup';
import Todo from './screens/Todo';
import Redirect from './screens/Redirect';
import Navbar from './components/Navbar';
import { useEffect, useState } from 'react';
import Logout from './screens/Logout';
function App() {
	const [signin, setSignin] = useState(false);
	useEffect(()=>{
		if(localStorage.getItem('refresh_token')){
			setSignin(true)
		}
	},[])
	return (
		<div className='container'>
			<Navbar signin={signin}/>
			<Routes>
				<Route path="/" element={<Home />} />
				<Route path="/signin" element={<Signin />} />
				<Route path="/signup" element={<Signup />} />
				<Route path="/todo/:id" element={<Todo />} />
				<Route path="/redirect" element={<Redirect signin={signin} setSignin={setSignin} />} />
				<Route path="/logout" element={<Logout />} />
			</Routes>
		</div>
	);
}

export default App;
