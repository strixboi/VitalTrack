package uwr.dbozek.vitaltrackfinal.db

import uwr.dbozek.vitaltrackfinal.SortType

data class ExerciseState(
    val exercises: List<Exercise> = emptyList(),
    val exerciseName: String = "",
    val caloriesValue: Float = 0f,
    val id: Int = 0,
    val isAddingExercise: Boolean = false,
    val sortType: SortType = SortType.ID
)
