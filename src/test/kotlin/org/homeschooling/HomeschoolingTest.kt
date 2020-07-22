package org.homeschooling

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HomeschoolingTest {

    private fun testSolution(inputString: String, family: FamilyAssignmentDistribution) {
//        println("---Verifying distribution of the assignment `$inputString`:\n$family---")
        val allTasks = parseAssignment(inputString)

        // 1. Sum scores, each score is equal Sum/3
        val scoreSum = allTasks.sumBy { it.points }
        assertEquals(scoreSum / 3.0, family.child1Tasks.sumBy { it.points }.toDouble())
        assertEquals(scoreSum / 3.0, family.child2Tasks.sumBy { it.points }.toDouble())
        assertEquals(scoreSum / 3.0, family.child3Tasks.sumBy { it.points }.toDouble())

        // 2. No repeating tasks, every task is used
        val taskSet = family.child1Tasks.toMutableSet()
        taskSet.addAll(family.child2Tasks)
        taskSet.addAll(family.child3Tasks)

        assertTrue(allTasks.size == taskSet.size &&
                allTasks.containsAll(taskSet) && taskSet.containsAll(allTasks))
    }

    @Test
    fun `should split the assignment1 by score among three children`() {
        val input = "A:1,B:1,C:1"
        val familyAssignmentDistribution = splitTasks(input)
        assertTrue(familyAssignmentDistribution.solutionFound)
        testSolution(input, familyAssignmentDistribution)
    }

    @Test
    fun `should split the assignment2 by score among three children`() {
        val input = "A:1,B:2,C:1,D:2,E:2,F:1,G:3,H:3"
        val familyAssignmentDistribution = splitTasks(input)
        assertTrue(familyAssignmentDistribution.solutionFound)
        testSolution(input, familyAssignmentDistribution)
    }

    @Test
    fun `should split the assignment3 by score among three children`() {
        val input = "A:5,B:4,C:1,D:2,E:7,F:8,G:3"
        val familyAssignmentDistribution = splitTasks(input)
        assertTrue(familyAssignmentDistribution.solutionFound)
        testSolution(input, familyAssignmentDistribution)
    }

    @Test
    fun `should indicate a solution can't be found if it's not possible to split among three children`() {
        val familyAssignmentDistribution = splitTasks("A:1,B:1")
        assertFalse(familyAssignmentDistribution.solutionFound)
    }

    @Test
    fun `should indicate a solution can't be found if it's not possible to split among three children - 2`() {
        val familyAssignmentDistribution = splitTasks("A:5,B:4,C:3,D:3")
        assertFalse(familyAssignmentDistribution.solutionFound)
    }

    @Test
    fun `should print "No" if tasks cannot be split`() {
        assertEquals("No", FamilyAssignmentDistribution(
                listOf(Task("A", 1)),
                listOf(Task("B", 3)),
                listOf(Task("C", 1)),
                false).toString())
    }

    @Test
    fun `should print "Yes" and children's assignments if tasks can be split`() {
        assertEquals("Yes\nChild 1: A:1, G:1\nChild 2: B:2\nChild 3: C:2",
            FamilyAssignmentDistribution(
                listOf(Task("A", 1), Task("G", 1)),
                listOf(Task("B", 2)),
                listOf(Task("C", 2)),
                true).toString())
    }
}