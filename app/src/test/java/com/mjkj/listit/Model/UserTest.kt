package com.mjkj.listit.Model

import org.junit.Test

class UserTest {

    @Test
    fun addListTest(){
        //Arrange
        val user = User("testUser", "user@test.com", "test123", null)
        val list = ListOfTasks("testList", "testCode", mutableListOf(), mutableListOf(), "#FFFFFFFF", "testDescription", user)
        //Act
        user.addList(list)
        //Assert
        assert(user.getLists()!!.size == 1)
        assert(user.getLists()!![0] == list)
    }
}