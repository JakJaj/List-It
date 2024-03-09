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
        val user = User("user1", "test@gmail.com", "123456789", null)
        //Act
        val testList: ListOfTasks = ListOfTasks.createList("testList", user, "#FFFFFFFF", "testDescription")
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
        assertEquals(testList, user.getLists()!![0])
    }
    @Test
    fun createCodeTest(){
        //Arrange
        var code = ""
        var code2 = ""
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
        val creator =  User("creator", "creator@test.com", "123456789", null)
        val user =  User("user", "user@test.com", "987654321", null)
        val testList = ListOfTasks.createList("testList", creator, "#FFFFFFFF", "testDescription")
        //Act
        testList.addUsers(user)
        //Assert
        assertNotNull(testList.getMembers())
        assertTrue(testList.getMembers()!!.size == 2)
        assertEquals(testList.getMembers()!![0], creator)
        assertEquals(testList.getMembers()!![1], user)
    }
    @Test
    fun addTaskToListTest() {
        //Arrange
        val creator = User("creator", "creator@test.com", "123456789", null)
        val testList = ListOfTasks.createList("testList", creator, "#FFFFFFFF", "testDescription")
        val task = Task.createTask("testTask", creator, "testDescription")
        //Act
        testList.addTask(task)
        //Assert
        assertNotNull(testList.getTasks())
        assertTrue(testList.getTasks()!!.size == 1)
        assertEquals(testList.getTasks()!![0], task)
        assertEquals(testList.getTasks()!![0].getCreator(), creator)
    }
}