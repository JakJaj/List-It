package com.mjkj.listit.Model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ListOfTasksTest {


    @Test
    fun createListTest() {
        //Arrange
        var user: User = User("user1", "test@gmail.com", "123456789", null)
        //Act
        var testList: ListOfTasks = ListOfTasks.createList("testList", user, "#FFFFFFFF", "testDescription")
        //Assert
        assertNotNull(testList)
        assertEquals(testList.getListName(), "testList")
        assertNotNull(testList.getCode())
        assertNotNull(testList.getTasks())
        assertNotNull(testList.getMembers())
        assertTrue(testList.getMembers()!!.size == 1)
        assertEquals(testList.getMembers()!![0], user)
        assertEquals(testList.getColor(), "#FFFFFFFF")
        assertEquals(testList.getDescription(), "testDescription")
        assertEquals(testList.getCreator(), user)
    }
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