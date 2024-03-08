package com.mjkj.listit.Model

class ListOfTasks(
    /**
     * The name of the list
     */
    private var listName: String,
    /**
     * The code of the list
     */
    private var code: String,
    /**
     * The tasks of the list
     */
    private var tasks: MutableList<Task>?,
    /**
     * The members of the list
     */
    private var members: MutableList<String>?,
    /**
     * The color of the list
     */
    private var color: String,
    /**
     * The description of the list
     */
    private var description: String?,
    /**
     * The creator of the list
     */
    private var creator: User?
) {
    companion object{
        /**
         * Static function to create a random code
         * @return a pseudorandom code for a list creation
         */
        fun createCode():String{
            val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            var code = ""
            for (i in 0..5) {
                code += characters[Math.floor(Math.random() * characters.length).toInt()]
            }
            return code
        }
    }
    fun getListName(): String {
        return listName
    }

    fun setListName(listName: String) {
        this.listName = listName
    }

    fun getCode(): String {
        return code
    }

    fun setCode(code: String) {
        this.code = code
    }

    fun getTasks(): MutableList<Task>? {
        return tasks
    }

    fun setTasks(tasks: MutableList<Task>?) {
        this.tasks = tasks
    }

    fun getMembers(): MutableList<String>? {
        return members
    }

    fun setMembers(members: MutableList<String>?) {
        this.members = members
    }

    fun getColor(): String {
        return color
    }

    fun setColor(color: String) {
        this.color = color
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