package com.mjkj.listit.Model

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskTest {

    @Test
    fun createTaskTest() {
        //Arrange
        val user = User.createUser("testUser", "user@test.com", "test123")
        //Act
        val task = Task.createTask("testTask", user, "testDescription")
        //Assert
        assertNotNull(task)
        assertEquals(task.getTaskName(), "testTask")
        assertEquals(task.getDescription(), "testDescription")
        assertEquals(task.getCreator(), user)
    }
    @Test
    fun codeGenerationTest() {
        //Arrange
        //Act
        val code = Task.generateCode()
        //Assert
        assertNotNull(code)
        assertEquals(code.length, 20)
        assertTrue(code.matches(Regex("^[a-zA-Z0-9]{20}$")))
    }
}