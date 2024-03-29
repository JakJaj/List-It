package com.mjkj.listit.Model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UserTest {

    @Test
    fun createUserTest(){
        //Arrange
        val testUser: User?
        //Act
        testUser = User.createUser("testUser", "testuser@test.com", "test123")
        //Assert
        assertNotNull(testUser)
        assertEquals("testUser", testUser.getName())
        assertEquals("testuser@test.com", testUser.getEmail())
        assertNull(testUser.getLists())
    }
    @Test
    fun addListTest(){
        //Arrange
        val user = User.createUser("testUser", "user@test.com", "test123")
        val list = ListOfTasks("testList", "testCode", mutableListOf(), mutableListOf(), "#FFFFFFFF", "testDescription", user)
        //Act
        user.addList(list)
        //Assert
        assertTrue(user.getLists()!!.size == 1)
        assertTrue(user.getLists()!![0] == list)
    }

    @Test
    fun removeListTest() {
        //Arrange
        val user = User.createUser("testUser", "user@test.com", "test123")
        val list = ListOfTasks(
            "testList",
            "testCode",
            mutableListOf(),
            mutableListOf(),
            "#FFFFFFFF",
            "testDescription",
            user
        )
        user.addList(list)
        //Act
        user.removeList(list)
        //Assert
        assertTrue(user.getLists()!!.isEmpty())
        assertEquals(0, user.getLists()!!.size)
    }
}