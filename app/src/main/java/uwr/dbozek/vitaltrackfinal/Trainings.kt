package uwr.dbozek.vitaltrackfinal

import uwr.dbozek.vitaltrackfinal.db.Exercise


var treningi: List<Training> = Trainings()
var cwiczenia: List<Workout> = Workouts()

fun Trainings(): List<Training>{
    return listOf(
        Training("2024-01-20", 200, "Bieganie"),
        Training("2024-01-21", 300, "Pływanie"),
        Training("2024-01-22", 250, "Jazda na rowerze"),
        Training("2024-01-23", 450, "Wspinaczka"),
        Training("2024-01-24", 300, "Pływanie"),
        Training("2024-01-25", 300, "Pływanie")
    )
}

fun addTraining(newTraining: Training): List<Training>{
    val updateList = treningi.toMutableList()
    updateList.add(newTraining)
    return updateList
}

fun Workouts(): List<Workout>{
    return listOf(
        Workout(exerciseName = "Bieganie", caloriesValue = 200),
        Workout(exerciseName = "Pływanie", caloriesValue = 300),
        Workout(exerciseName = "Joga", caloriesValue = 100),
        Workout(exerciseName = "Koszykówka", caloriesValue = 400),
        Workout(exerciseName = "Piłka nożna", caloriesValue = 350),
        Workout(exerciseName = "Jazda na rowerze", caloriesValue = 250),
        Workout(exerciseName = "Podnoszenie ciężarów", caloriesValue = 500),
        Workout(exerciseName = "Tenis", caloriesValue = 350),
        Workout(exerciseName = "Siatkówka", caloriesValue = 300),
        Workout(exerciseName = "Wspinaczka", caloriesValue = 450),
        Workout(exerciseName = "Pilates", caloriesValue = 150),
        Workout(exerciseName = "Narciarstwo", caloriesValue = 400),
        Workout(exerciseName = "Zumba", caloriesValue = 200),
        Workout(exerciseName = "Golf", caloriesValue = 180),
        Workout(exerciseName = "Surfng", caloriesValue = 300)
    )
}

fun addWorkout(newWorkout: Workout): List<Workout>{
    val updateList = cwiczenia.toMutableList()
    updateList.add(newWorkout)
    return updateList
}