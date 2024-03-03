package com.mjkj.listit.Model

class User {
    /**
     * The name of the user
     */
    private var name: String
    /**
     * The email of the user
     */
    private var email: String
    /**
     * The password of the user
     */
    private var password: String
    /**
     * The lists of the user
     */
    private var lists: MutableList<ListOfTasks>?

    constructor(name: String, email: String, password: String, lists: MutableList<ListOfTasks>?) {
        this.name = name
        this.email = email
        this.password = password
        this.lists = lists
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
}