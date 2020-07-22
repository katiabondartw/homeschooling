package org.homeschooling

data class FamilyAssignmentDistribution(var child1Tasks : List<Task> = emptyList(),
                                        var child2Tasks : List<Task> = emptyList(),
                                        var child3Tasks : List<Task> = emptyList(),
                                        var solutionFound : Boolean = false) {

    override fun toString(): String {
        return when (solutionFound) {
            false -> "No"
            true -> buildString {
                append("Yes\n")
                append("Child 1: ${getChildTasksString(child1Tasks)}\n")
                append("Child 2: ${getChildTasksString(child2Tasks)}\n")
                append("Child 3: ${getChildTasksString(child3Tasks)}")
            }
        }
    }

    private fun getChildTasksString(tasks: List<Task>) : String {
        return tasks.joinToString(", ")
    }

}

fun splitTasks(assignmentInput: String) : FamilyAssignmentDistribution {
    val allTasks = parseAssignment(assignmentInput)
    return splitByPoints(allTasks)
}

private fun splitByPoints(allTasks: List<Task>) : FamilyAssignmentDistribution {
    var family = FamilyAssignmentDistribution()
    val pointsSum = allTasks.sumBy { it.points }
    if (pointsSum % 3 > 0) {
        return family
    }
    val pointsPerChild = pointsSum / 3
    val undistributedTasks = allTasks.sortedByDescending { it.points }.toMutableList()

    family.child1Tasks = selectTasksForTargetPoints(undistributedTasks, pointsPerChild)
    if (family.child1Tasks.sumBy { it.points } != pointsPerChild)
        return family
    undistributedTasks.removeAll(family.child1Tasks)

    family.child2Tasks = selectTasksForTargetPoints(undistributedTasks, pointsPerChild)
    if (family.child2Tasks.sumBy { it.points } != pointsPerChild)
        return family
    undistributedTasks.removeAll(family.child2Tasks)

    family.child3Tasks = selectTasksForTargetPoints(undistributedTasks, pointsPerChild)
    if (family.child3Tasks.sumBy { it.points } != pointsPerChild)
        return family

    family.solutionFound = true
    return family
}

private fun selectTasksForTargetPoints(allTasks: List<Task>, targetPointsSum: Int) : List<Task> {
    if (allTasks.isEmpty())
        return emptyList()

    if (allTasks.first().points == targetPointsSum)
        return listOf(allTasks.first())

    if (allTasks.size == 1)
        return emptyList()

    if (allTasks.first().points > targetPointsSum)
        return selectTasksForTargetPoints(allTasks.subList(1, allTasks.size), targetPointsSum)

    return listOf(allTasks.first()) +
            selectTasksForTargetPoints(
                allTasks.subList(1, allTasks.size), targetPointsSum - allTasks.first().points)
}