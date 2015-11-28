require 'test_helper'

class Todo::TodosControllerTest < ActionController::TestCase
  setup do
    @todo_todo = todo_todos(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:todo_todos)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create todo_todo" do
    assert_difference('Todo::Todo.count') do
      post :create, todo_todo: {  }
    end

    assert_redirected_to todo_todo_path(assigns(:todo_todo))
  end

  test "should show todo_todo" do
    get :show, id: @todo_todo
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @todo_todo
    assert_response :success
  end

  test "should update todo_todo" do
    patch :update, id: @todo_todo, todo_todo: {  }
    assert_redirected_to todo_todo_path(assigns(:todo_todo))
  end

  test "should destroy todo_todo" do
    assert_difference('Todo::Todo.count', -1) do
      delete :destroy, id: @todo_todo
    end

    assert_redirected_to todo_todos_path
  end
end
