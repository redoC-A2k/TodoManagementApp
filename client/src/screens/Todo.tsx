import axios, { AxiosError } from 'axios';
import React, { FormEvent, useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getToken } from '../utils/utils';

const Todo = () => {
	const [todoTitle, setTodoTitle] = useState('');
	const [todoDescription, setTodoDescription] = useState('');
	const [buttonText, setButtonText] = useState('Add Todo');

	const { id } = useParams();
	const navigate = useNavigate();

	let getTodo = useCallback(async (id: Number) => {
		let token = await getToken();
		let response = await axios.get(`${process.env.REACT_APP_BACKEND}/api/todo/${id}`, {
			headers: {
				Authorization: `Bearer ${token}`
			}
		})
		setTodoTitle(response.data.title);
		setTodoDescription(response.data.description)
		setButtonText('Update Todo')
	}, [])

	useEffect(() => {
		if (localStorage.getItem("refresh_token")) {
			if (id == "create") {
				setTodoTitle('');
				setTodoDescription('')
				setButtonText('Add Todo')
			} else {
				getTodo(Number(id));
			}
		} else {
			navigate('/signup')
		}
	}, [id])

	const onFormSubmit = async (e: FormEvent) => {
		e.preventDefault();
		try {
			let token = await getToken();

			if (id == "create") {
				let response = await axios.post(`${process.env.REACT_APP_BACKEND}/api/todo`, {
					title: todoTitle,
					description: todoDescription
				}, {
					headers: {
						Authorization: `Bearer ${token}`
					}
				})
				console.log(response.data)
			} else {
				let response = await axios.put(`${process.env.REACT_APP_BACKEND}/api/todo/${id}`, {
					title: todoTitle,
					description: todoDescription
				}, {
					headers: {
						Authorization: `Bearer ${token}`
					}
				})
				console.log(response.data)
			}
			navigate('/')
		} catch (error: AxiosError | any) {
			if (error.response && error.response.data) {
				console.log(error.response.data)
			} else if (error.response)
				console.log(error.response)
			else
				console.log(error)
		}
	};

	return (
		<div id='todo'>
			<form onSubmit={onFormSubmit} className='myform'>
				<div className="mb-3 row">
					<div className="col-3">
						<label htmlFor="todoTitle" className="form-label">Title :</label>
					</div>
					<div className="col-9">
						<input type="text" className="form-control" placeholder='Enter todo title' name='title' value={todoTitle} onChange={(e) => setTodoTitle(e.target.value)} required />
					</div>
				</div>
				<div className="mb-3 row">
					<div className="col-3">
						<label htmlFor="todoDescription" className="form-label">Description :</label>
					</div>
					<div className="col-9">
						<input type="text" className="form-control" placeholder='Enter todo description' name='title' value={todoDescription} onChange={(e) => setTodoDescription(e.target.value)} required />
					</div>
				</div>
				<button type="submit">{buttonText}</button>
			</form>
		</div>
	);
};

export default Todo
