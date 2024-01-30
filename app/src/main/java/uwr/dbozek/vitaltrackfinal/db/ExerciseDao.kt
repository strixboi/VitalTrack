package uwr.dbozek.vitaltrackfinal.db


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)

    @Query("INSERT INTO exercise (exerciseName, caloriesValue) VALUES (:exerciseName,:caloriesValue)")
    fun DODAJNOWYEXERCISE(exerciseName: String, caloriesValue: Float): Flow<List<Exercise>>


    @Query("DELETE FROM exercise WHERE id = :exerciseId")
    fun deleteExercise(exerciseId: Long)


    @Query("SELECT * FROM exercise ORDER BY caloriesValue ASC")
    fun getExercisesOrderedByCalories(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise ORDER BY id ASC")
    fun getExercisesOrderedById(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise ORDER BY exerciseName ASC")
    fun getExercisesOrderedByName(): Flow<List<Exercise>>
}