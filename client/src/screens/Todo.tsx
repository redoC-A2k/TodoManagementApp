import React, { FormEvent, useState } from 'react';

const Todo = () => {
  const [todoText, setTodoText] = useState('');

  const handleAddTodo = (e:FormEvent) => {
    e.preventDefault();
    // Add your logic to add todo here
    setTodoText('');
  };

  return (
    <div className="add-todo-container">
      <h2>Todo</h2>
      <form onSubmit={handleAddTodo}>
        <input
          type="text"
          placeholder="Enter Todo"
          value={todoText}
          onChange={(e) => setTodoText(e.target.value)}
        />
        <button type="submit">Add Todo</button>
      </form>
    </div>
  );
};

export default Todo
