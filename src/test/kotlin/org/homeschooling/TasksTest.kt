package org.homeschooling

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class TasksTest {

    @Test
    fun `should parse assignment input and return list of tasks`() {
        assertIterableEquals(
            listOf(
                Task("A", 5),
                Task("B", 4),
                Task("C", 10),
                Task("D", 2),
                Task("F", 8),
                Task("GO", 3)
            ), parseAssignment("A:5,B:4,C:10,D:2,F:8,GO:3"))
    }

    @Test
    fun `should return error message if empty assignment input is given`() {
        val exceptionThatWasThrown: Throwable = assertThrows(TaskParserException::class.java) {
            parseAssignment("")
        }
        assertEquals("Empty assignment is not valid. Please use the format: A:1,B:21,CC:3",
            exceptionThatWasThrown.message)
    }

    @Test
    fun `should return error message if invalid assignment input is given`() {
        val exceptionThatWasThrown: Throwable = assertThrows(TaskParserException::class.java) {
            parseAssignment("A:2-B:3")
        }
        assertEquals("Invalid assignment string is given: A:2-B:3.\nPlease use the format: A:1,B:21,CC:3",
            exceptionThatWasThrown.message)
    }

    @Test
    fun `should return error message if duplicate task names are given`() {
        // TODO
    }

    @Test
    fun `should return error message if non-positive points are given`() {
        // TODO
    }

}