import axios, { AxiosError } from 'axios';
import React, { FormEvent, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Signup = () => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [name, setName] = useState('');
	const navigate = useNavigate()

	const handleSignup = async (e: FormEvent) => {
		e.preventDefault();
		try {
			let response = await axios.post(`${process.env.REACT_APP_BACKEND}/api/users/signup`, {
				email:username,
				password,
				name
			})
			console.log(response.data)
			alert("Account creation successful \n Signin with created account")
			navigate('/signin')
		} catch (error:AxiosError|any) {
			if(error.response && error.response.data)
			console.log(error.response.data)
			else if (error.response ) console.error(error.response)
			else console.log(error)
		}
	};

	return (
		<div className="signup-container">
			<h2 className='mt-5'><center>Sign Up</center></h2>
			<div className="row mt-5">
				<div className="col-10 col-md-6 offset-md-3 offset-1">
					<form onSubmit={handleSignup} className='myform'>
						<div className="mb-3 row">
							<div className="col-5">
								<label htmlFor="Name" className="form-label">Name :</label>
							</div>
							<div className="col-7">
								<input type="Name" className="form-control" placeholder='Enter Name' name='Name' value={name} onChange={(e) => setName(e.target.value)} id="Name" required/>
							</div>
						</div>
						<div className="mb-3 row">
							<div className="col-5">
								<label htmlFor="email" className="form-label">Email :</label>
							</div>
							<div className="col-7">
								<input type="email" className="form-control" placeholder='Enter email' name='email' value={username} onChange={(e) => setUsername(e.target.value)} id="email" required/>
							</div>
						</div>
						<div className="mb-3 row">
							<div className="col-5">
								<label htmlFor="password" className="form-label">Password :</label>
							</div>
							<div className="col-7">
								<input type="password" className="form-control" placeholder='Enter password' name='password' value={password} onChange={(e) => setPassword(e.target.value)} id="password" required/>
							</div>
						</div>
						<button type="submit" className="btn btn-primary">Submit</button>
						<Link style={{fontSize:"1.6rem"}} className="btn btn-light ms-3" to='/signin' role="button">Signin</Link>
					</form>
				</div>
			</div>
		</div>
	);
};

export default Signup;