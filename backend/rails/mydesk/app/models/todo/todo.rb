class Todo::Todo < ActiveRecord::Base
  validates :userid, presence: true
  validates :title, presence: true
end
