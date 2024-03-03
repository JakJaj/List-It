package com.mjkj.listit.Model

class Task(
    taskName: String,
    /**
     * The description of the task
     */
    private var description: String?, color: String, creator: User?, list: ListOfTasks?
) {
    /**
     * The name of the task
     */
    private var title: String = taskName

    /**
     * The creator of the task
     */
    private var author: User? = creator

    fun getTaskName(): String {
        return title
    }

    fun setTaskName(taskName: String) {
        this.title = taskName
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getCreator(): User? {
        return author
    }

    fun setCreator(creator: User?) {
        this.author = creator
    }
}