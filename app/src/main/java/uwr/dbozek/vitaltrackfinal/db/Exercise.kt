package uwr.dbozek.vitaltrackfinal.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Exercise(
    val exerciseName: String,
    val caloriesValue: Float,
    @PrimaryKey(autoGenerate = true)
    val id: Int=0
)
