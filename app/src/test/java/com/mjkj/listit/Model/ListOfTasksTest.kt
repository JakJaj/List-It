package com.mjkj.listit.Model

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
}