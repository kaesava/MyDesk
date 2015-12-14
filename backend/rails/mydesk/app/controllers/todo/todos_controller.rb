class Todo::TodosController < ApplicationController
  before_action :set_todo_todo, only: [:show, :edit, :update, :destroy]

  # GET /todo/todos
  # GET /todo/todos.json
  def index
    @todo_todos = Todo::Todo.all
  end

  # GET /todo/todos/1
  # GET /todo/todos/1.json
  def show
  end

  # GET /todo/todos/new
  def new
    @todo_todo = Todo::Todo.new
  end

  # GET /todo/todos/1/edit
  def edit
  end

  # POST /todo/todos
  # POST /todo/todos.json
  def create
    @todo_todo = Todo::Todo.new(todo_todo_params)

    respond_to do |format|
      if @todo_todo.save
        format.html { redirect_to @todo_todo, notice: 'Todo was successfully created.' }
        format.json { render :show, status: :created, location: @todo_todo }
      else
        format.html { render :new }
        format.json { render json: @todo_todo.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /todo/todos/1
  # PATCH/PUT /todo/todos/1.json
  def update
    respond_to do |format|
      if @todo_todo.update(todo_todo_params)
        format.html { redirect_to @todo_todo, notice: 'Todo was successfully updated.' }
        format.json { render :show, status: :ok, location: @todo_todo }
      else
        format.html { render :edit }
        format.json { render json: @todo_todo.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /todo/todos/1
  # DELETE /todo/todos/1.json
  def destroy
    @todo_todo.destroy
    respond_to do |format|
      format.html { redirect_to todo_todos_url, notice: 'Todo was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_todo_todo
      @todo_todo = Todo::Todo.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def todo_todo_params
      params.permit(:id, :userid, :title, :details, :duedate, :completed)
    end
end
