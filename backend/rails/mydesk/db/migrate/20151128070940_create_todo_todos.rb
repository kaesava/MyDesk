class CreateTodoTodos < ActiveRecord::Migration
  def change
    create_table :todo_todos do |t|

      t.timestamps null: false
    end
  end
end
