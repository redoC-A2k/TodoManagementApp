import { useEffect } from "react"
import { Link, useNavigate } from "react-router-dom"

export default function Navbar({ signin }: { signin: boolean }) {
    const navigate = useNavigate()
    return (
        <section id="nav" className="mb-4">
            {signin ?
                <nav className="navbar navbar-expand-lg bg-body-tertiary">
                    <div className="container-fluid">
                        <Link className="navbar-brand me-3" to="/"><img width="40" height="32" src="https://img.icons8.com/color/96/todo-list--v1.png" alt="todo-list--v1" /></Link>
                        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul className="navbar-nav nav me-auto mb-2 ">
                                <li className="nav-item me-3">
                                    <Link className="nav-link" to="/">Home</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/todo/create">Add Todo</Link>
                                </li>
                            </ul>
                            <ul className="navbar-nav">
                                <li className="nav-item">
                                    <Link className="nav-link" to="/logout">Logout</Link>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                :
                <></>
            }
        </section>
    )
}