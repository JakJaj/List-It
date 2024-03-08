package com.mjkj.listit.Model

class Task(
    /**
     * The name of the task
     */
    private var taskName: String,
    /**
     * The description of the task
     */
    private var description: String?,
    /**
     * The creator of the task
     */
    private var creator: User?
) {
    companion object{
        /**
         * Static function to create a task
         * @param taskName the name of the task
         * @param creator the creator of the task
         * @param description the description of the task
         * @return a new task
         */
        fun createTask(taskName: String, creator: User, description: String?): Task{
            return Task(taskName, description, creator)
        }
    }
    fun getTaskName(): String {
        return taskName
    }

    fun setTaskName(taskName: String) {
        this.taskName = taskName
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getCreator(): User? {
        return creator
    }

    fun setCreator(creator: User?) {
        this.creator = creator
    }

}