package com.mjkj.listit.Model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ListOfTasksTest {


    @Test
    fun createCodeTest(){
        //Arrange
        var code:String = ""
        var code2:String = ""
        //Act
        code = ListOfTasks.createCode()
        code2 = ListOfTasks.createCode()
        //Assert
        assertNotNull(code)
        assertTrue(code.length == 6)
        assertNotEquals(code, code2)
        assertTrue(code.matches(Regex("^[a-zA-Z0-9]{6}$")))
    }

    @Test
    fun addUsersToListTest(){
        //Arrange
        var user:User =  User("user1", "test@gmail.com", "123456789", null)
        var testList:ListOfTasks = ListOfTasks("testList", ListOfTasks.createCode(), mutableListOf(), null, "#FFFFFFFF", null, null)
        //Act
        testList.addUsers(user)
        //Assert
        assertNotNull(testList.getMembers())
        assertTrue(testList.getMembers()!!.size == 1)
        assertEquals(testList.getMembers()!![0], user)
    }
    
}