package org.homeschooling

import java.lang.Exception

const val FORMAT_HINT = "A:1,B:21,CC:3"

data class Task(val name: String, val points: Int) {
    override fun toString(): String {
        return "$name:$points"
    }
}

class TaskParserException(message:String): Exception(message)

fun parseAssignment(assignmentInput: String) : List<Task> {
    validateAssignmentInput(assignmentInput)

    return assignmentInput.trim().split(",").map {
        Task(it.substringBefore(":"),
            it.substringAfter(":").toInt())
    }
}

private fun validateAssignmentInput(assignmentInput: String) {

    if (assignmentInput.isEmpty())
        throw TaskParserException("Empty assignment is not valid. Please use the format: $FORMAT_HINT")

    if (!"(\\w+:\\d+)(,\\w+:\\d+)*".toRegex().matches(assignmentInput))
        throw TaskParserException("Invalid assignment string is given: $assignmentInput.\nPlease use the format: $FORMAT_HINT")
}