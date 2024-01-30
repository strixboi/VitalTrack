package uwr.dbozek.vitaltrackfinal.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Exercise::class],
    version = 1
)



abstract class ExerciseDatabase: RoomDatabase() {
    abstract val dao: ExerciseDao
}