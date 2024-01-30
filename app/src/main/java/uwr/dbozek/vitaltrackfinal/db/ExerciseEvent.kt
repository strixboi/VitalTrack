package uwr.dbozek.vitaltrackfinal.db

import uwr.dbozek.vitaltrackfinal.SortType

sealed interface ExerciseEvent {
    object SaveExercise: ExerciseEvent
    object EditExercise: ExerciseEvent
    data class SetExerciseName(val exerciseName: String): ExerciseEvent
    data class SetCaloriesValue(val caloriesValue: Float): ExerciseEvent
    object ShowDialog: ExerciseEvent
    object HideDialog: ExerciseEvent
    data class SortExercises(val sortType: SortType): ExerciseEvent
    //data class DeleteExercise(val exercise: Exercise): ExerciseEvent

}