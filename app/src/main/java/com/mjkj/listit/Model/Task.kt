package com.mjkj.listit.Model

class Task(
    /**
     * The code of the task
     */
    private var code: String,
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
         * Static function to generate a task
         * @param taskName the name of the task
         * @param creator the creator of the task
         * @param description the description of the task
         * @return a new task
         */
        fun generateTask(taskName: String, creator: User, description: String?): Task{
            val code = generateCode()
            return Task(code, taskName, description, creator)
        }
        fun createTask(code: String, taskName: String, description: String?, creator: User, status: Boolean): Task{
            return Task(code, taskName, description, creator, status)
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
    fun getCode(): String {
        return code
    }
    fun setCode(code: String) {
        this.code = code
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