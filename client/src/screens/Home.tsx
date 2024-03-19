import axios, { AxiosError } from "axios";
import { ChangeEvent, useCallback, useEffect, useState } from "react";
import { getToken } from "../utils/utils";
import { useNavigate } from "react-router-dom";

const Home = () => {
	const [todos, setTodos] = useState([])
	const navigate = useNavigate()

	const getTodos = useCallback(async function () {
		let token;
		// try {
		token = await getToken()
		let response = await axios.get(`${process.env.REACT_APP_BACKEND}/api/todo`, {
			headers: {
				Authorization: `Bearer ${token}`
			}
		})
		setTodos(response.data)
	}, [])

	useEffect(() => {
		if (localStorage.getItem('refresh_token')) {
			getTodos()
		} else {
			navigate('/signup')
		}
	}, [])

	const editTodo = useCallback((id: Number) => {
		navigate(`/todo/${id}`)
	}, [])

	const doneTodo = useCallback(async (id: Number) => {
		try {
			let token = await getToken()
			let response = await axios.delete(`${process.env.REACT_APP_BACKEND}/api/todo/${id}`, {
				headers: {
					Authorization: `Bearer ${token}`
				}
			})
			setTodos(response.data)
		} catch (error: AxiosError | any) {
			if (error.response && error.response.data)
				console.log(error.response.data)
			else if (error.response)
				console.log(error.response)
			else console.log(error)
		}
	}, [])


	return (
		<section id="todos">
			<h2>Todo List</h2>
			<div className="todos">
				{todos.length > 0
					?
					todos.map((todo: any) => (
						<div key={todo.id} className="card mb-2">
							<div className="card-body">
								<h5 className="card-title">
									<div>
										<span>{todo.title}</span>
										<div>
											<i onClick={() => editTodo(todo.id)} className="fa-solid fa-pen" ></i>
											<i onClick={() => doneTodo(todo.id)} className="fa-solid fa-circle-check" style={{ color: "green", fontSize: "2rem" }}></i>
										</div>
									</div>
								</h5>
								<h6 className="card-subtitle mb-2 text-body-secondary">{todo.description}</h6>
							</div>
						</div>
					)) :
					<span className="list-group-item">No todos</span>}
			</div>
		</section>
	);
};

export default Home;
