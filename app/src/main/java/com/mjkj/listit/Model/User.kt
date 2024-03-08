package com.mjkj.listit.Model

class User(
    /**
     * The name of the user
     */
    private var name: String,
    /**
     * The email of the user
     */
    private var email: String,
    /**
     * The password of the user
     */
    private var password: String,
    /**
     * The lists of the user
     */
    private var lists: MutableList<ListOfTasks>?
) {

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

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getLists(): MutableList<ListOfTasks>? {
        return lists
    }

    fun setLists(lists: MutableList<ListOfTasks>?) {
        this.lists = lists
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
}