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
    private var creator: User?,
    /**
     * The status of the task
     * True if the task is done, false otherwise
     */
    private var status: Boolean = false
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
        /**
         * Static function to generate a random code
         * @return a random code
         */
        fun generateCode(): String {
            val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val length = 20
            var code = ""
            for (i in 0 until length) {
                code += chars[Math.floor(Math.random() * chars.length).toInt()]
            }
            return code
        }
    }

    fun getStatus(): Boolean {
        return status
    }
    fun setStatus(status: Boolean) {
        this.status = status
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