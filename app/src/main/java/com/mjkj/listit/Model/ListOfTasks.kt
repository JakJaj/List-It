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
    private var members: MutableList<User>?,
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

        /**
         * Static function to create a list
         * @param listName the name of the list
         * @param creator the creator of the list
         * @param color the color of the list
         * @param description the description of the list
         * @return a new list
         */
        fun createList(listName: String, creator: User, color:String, description: String?): ListOfTasks{
            val newList = ListOfTasks(listName, createCode(), mutableListOf(), mutableListOf(creator), color, description, creator)
            creator.addList(newList)
            return newList
        }
    }
    /**
     * Function to add a user to the list
     * @param user the user to add
     */
    fun addUsers(user: User){
        if(members == null){
            members = mutableListOf()
        }
        members!!.add(user)
    }
    /**
     * Function to add a task to the list
     * @param task the task to add
     */
    fun addTask(task: Task){
        if(tasks == null){
            tasks = mutableListOf()
        }
        tasks!!.add(task)
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

    fun getMembers(): MutableList<User>? {
        return members
    }

    fun setMembers(members: MutableList<User>?) {
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