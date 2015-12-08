json.array!(@todo_todos) do |todo_todo|
	json.extract! todo_todo, :id, :duedate, :title, :details, :userid, :completed
end
