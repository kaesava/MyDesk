class AddColsToTodo < ActiveRecord::Migration
  def change
    add_column :todo_todos, :userid, :integer
    add_column :todo_todos, :duedate, :date
    add_column :todo_todos, :title, :string
    add_column :todo_todos, :details, :string
    add_column :todo_todos, :completed, :boolean
  end
end
