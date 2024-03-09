package com.mjkj.listit.Model

class User(
    /**
     * The id of the user
     */
    private var id: String?,
    /**
     * The name of the user
     */
    private var name: String,
    /**
     * The email of the user
     */
    private var email: String,
    /**
     * The lists of the user
     */
    private var lists: MutableList<ListOfTasks>?
) {
    companion object{
        /**
         * Static function to create a user
         * @param name the name of the user
         * @param email the email of the user
         * @param password the password of the user
         * @return a new user
         */
        fun createUser(id:String?, name: String, email: String): User{
            return User(id, name, email, null)
        }
    }

    /**
     * Function to add a list to the user
     * @param newList the list to add
     * @return the lists of the user
     */
    fun addList(newList: ListOfTasks): MutableList<ListOfTasks>? {
        if(lists == null) {
            lists = mutableListOf()
        }
        lists?.add(newList)
        return lists
    }
    /**
     * Function to remove a list from the user
     * @param list the list to remove
     * @return the lists of the user
     */
    fun removeList(list: ListOfTasks): MutableList<ListOfTasks>? {
        lists?.remove(list)
        return lists
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }
    fun getLists(): MutableList<ListOfTasks>? {
        return lists
    }

    fun setLists(lists: MutableList<ListOfTasks>?) {
        this.lists = lists
    }
    fun getId(): String? {
        return id
    }
    fun setId(id: String) {
        this.id = id
    }
}