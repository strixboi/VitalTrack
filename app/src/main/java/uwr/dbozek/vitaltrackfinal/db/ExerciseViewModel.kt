package uwr.dbozek.vitaltrackfinal.db


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uwr.dbozek.vitaltrackfinal.SortType
import java.lang.Exception

class ExerciseViewModel (
    private val dao: ExerciseDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.ID)
    private val _exercises = _sortType
        .flatMapLatest { _sortType ->
            when(_sortType){
                SortType.ID -> dao.getExercisesOrderedById()
                SortType.EXERCISE_NAME -> dao.getExercisesOrderedByName()
                SortType.CALORIES_VALUE ->dao.getExercisesOrderedByCalories()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ExerciseState())
    val state = combine(_state,_sortType,_exercises){ state, sortType, exercises ->
        state.copy(
            exercises = exercises,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseState())


    fun onEvent(event: ExerciseEvent){
        when(event){

//            is ExerciseEvent.DeleteExercise -> {
//                viewModelScope.launch {
//                    dao.deleteExercise(event.exercise)
//                }
//            }
            ExerciseEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingExercise = false
                ) }
            }
            ExerciseEvent.SaveExercise -> {
                val exerciseName = state.value.exerciseName
                val caloriesValue = state.value.caloriesValue
                Log.d("HALO","${exerciseName}, ${caloriesValue}")

                if(exerciseName.isBlank() || caloriesValue.isNaN()){
                    return
                }

                Log.d("HALO", "BŁĄD")

                val exercise = Exercise(
                    exerciseName = exerciseName,
                    caloriesValue = caloriesValue,
                )
                Log.d("HALO", "BŁĄD56+")


                try{
                    viewModelScope.launch {

                        dao.DODAJNOWYEXERCISE(exerciseName,caloriesValue)
                    }
                } catch (e: Exception){
                    Log.e("HALO","Błąd tutaj")
                    Log.d("HALO", "BŁĄD tutaj sie wydarzyl")
                }


                _state.update { it.copy(
                    isAddingExercise = false,
                    exerciseName = "",
                    caloriesValue = 0f,
                ) }

            }

            ExerciseEvent.EditExercise ->{
                val id = state.value.id
                val exerciseName = state.value.exerciseName
                val caloriesValue = state.value.caloriesValue

                if(exerciseName.isBlank() || caloriesValue.isNaN()){
                    return
                }

                val exercise = Exercise(
                    id =id,
                    exerciseName = exerciseName,
                    caloriesValue = caloriesValue
                )

                viewModelScope.launch {
                    dao.insertExercise(exercise)
                }

                _state.update {
                    it.copy(
                        isAddingExercise = false,
                        exerciseName = "",
                        caloriesValue = 0f
                    )
                }
            }

            is ExerciseEvent.SetExerciseName -> {
                _state.update { it.copy(
                    exerciseName = event.exerciseName
                ) }
            }
            is ExerciseEvent.SetCaloriesValue -> {
                _state.update { it.copy(
                    caloriesValue = event.caloriesValue
                ) }
            }

            ExerciseEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingExercise = true
                ) }
            }

            is ExerciseEvent.SortExercises -> {
                _sortType.value = event.sortType
            }
        }
    }
}