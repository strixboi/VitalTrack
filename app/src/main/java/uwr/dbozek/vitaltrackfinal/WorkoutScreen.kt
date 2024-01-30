package uwr.dbozek.vitaltrackfinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.NonCancellable.key
import androidx.compose.runtime.key
import androidx.compose.ui.unit.sp

import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen() {
    var workouts by remember { mutableStateOf(cwiczenia) }
    var showDialog by remember { mutableStateOf(false) }
    var newExerciseName by remember { mutableStateOf("") }
    var newCaloriesValue by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = "Twoje Treningi", color = Color.Red)
            },
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        ) {

            items(cwiczenia) { workout ->
                WorkoutItem(workout = workout)
            }
        }

        AddWorkoutButton(
            onClick = {
                showDialog = true
            },
            onAddWorkout = {
                workouts = workouts + it
            }
        )

        // Okno dialogowe do dodawania treningu
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = "Dodaj nowy trening")
                },
                text = {
                    Column {
                        TextField(
                            value = newExerciseName,
                            onValueChange = {
                                newExerciseName = it
                            },
                            label = {
                                Text("Nazwa treningu")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = newCaloriesValue,
                            onValueChange = {
                                newCaloriesValue = it
                            },
                            label = {
                                Text("Kalorie do spalenia")
                            }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newExerciseName.isNotBlank() && newCaloriesValue.isNotBlank()) {
                                val workout = Workout(
                                    exerciseName = newExerciseName,
                                    caloriesValue = newCaloriesValue.toInt()
                                )
                                workouts = workouts + workout
                                showDialog = false
                            }
                        }
                    ) {
                        Text(text = "Dodaj")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Anuluj")
                    }
                }
            )
        }
    }
}

//val sampleWorkouts = listOf(
//    Workout(exerciseName = "Bieganie", caloriesValue = 200),
//    Workout(exerciseName = "Pływanie", caloriesValue = 300),
//    Workout(exerciseName = "Joga", caloriesValue = 100),
//    Workout(exerciseName = "Koszykówka", caloriesValue = 400),
//    Workout(exerciseName = "Piłka nożna", caloriesValue = 350),
//    Workout(exerciseName = "Jazda na rowerze", caloriesValue = 250),
//    Workout(exerciseName = "Podnoszenie ciężarów", caloriesValue = 500),
//    Workout(exerciseName = "Tenis", caloriesValue = 350),
//    Workout(exerciseName = "Siatkówka", caloriesValue = 300),
//    Workout(exerciseName = "Wspinaczka", caloriesValue = 450),
//    Workout(exerciseName = "Pilates", caloriesValue = 150),
//    Workout(exerciseName = "Narciarstwo", caloriesValue = 400),
//    Workout(exerciseName = "Zumba", caloriesValue = 200),
//    Workout(exerciseName = "Golf", caloriesValue = 180),
//    Workout(exerciseName = "Surfing", caloriesValue = 300)
//)

@Composable
fun AddWorkoutButton(
    onClick: () -> Unit,
    onAddWorkout: (Workout) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var exerciseName by remember { mutableStateOf("") }
    var caloriesValue by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Dodaj nowy trening")
            },
            text = {
                Column {
                    TextField(
                        value = exerciseName,
                        onValueChange = {
                            exerciseName = it
                        },
                        label = {
                            Text("Nazwa treningu")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = caloriesValue,
                        onValueChange = {
                            caloriesValue = it
                        },
                        label = {
                            Text("Kalorie do spalenia")
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (exerciseName.isNotBlank() && caloriesValue.isNotBlank()) {
                            val workout = Workout(
                                exerciseName = exerciseName,
                                caloriesValue = caloriesValue.toInt()
                            )

                            cwiczenia = addWorkout(workout)
                            showDialog = false
                        }
                    }
                ) {
                    Text(text = "Dodaj")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "Anuluj")
                }
            }
        )
    }

    OutlinedButton(
        onClick = {
            showDialog = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 70.dp)
            .padding(16.dp)
            .padding(bottom = 70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Dodaj",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Dodaj nowy trening",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun WorkoutItem(workout: Workout) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        // Okno dialogowe
        AlertDialog(
            onDismissRequest = {
                // Zamknij okno dialogowe po kliknięciu poza nim
                showDialog = false
            },
            title = {
                Text(text = workout.exerciseName)
            },
            text = {
                Text(text = "Kalorie do spalenia: ${workout.caloriesValue}")
            },
            confirmButton = {
                Button(
                    onClick = {
                        treningi = addTraining(Training("2024-01-26",workout.caloriesValue,workout.exerciseName))
                        showDialog = false
                    }
                ) {
                    Text(text = "Wykonane!")
                }
            }
        )
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = {
            showDialog = true
        }
    ) {
        Text(
            text = "${workout.exerciseName} - ${workout.caloriesValue} kalorii" ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
        )
    }
}