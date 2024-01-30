package uwr.dbozek.vitaltrackfinal.db


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextField


@Composable
fun AddWorkoutDialog(
    state: ExerciseState,
    onEvent: (ExerciseEvent) -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ExerciseEvent.HideDialog)
        },
        title = { Text(text = "Add Exercise") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.exerciseName,
                    onValueChange = {
                        onEvent(ExerciseEvent.SetExerciseName(it))
                    },
                    placeholder = {
                        Text(text = "Exercise")
                    }
                )
                var isDropdownMenuVisible by remember { mutableStateOf(false) }

                Box {
                    Text(
                        text = state.caloriesValue.toString(),
                        modifier = Modifier
                            .clickable { isDropdownMenuVisible = true }
                            .background(Color.Gray)
                            .padding(16.dp)
                    )

                    DropdownMenu(
                        expanded = isDropdownMenuVisible,
                        onDismissRequest = { isDropdownMenuVisible = false }
                    ) {
                        listOf(200.0f, 300.0f, 400f, 500f, 600f, 700f).forEach { gradeValue ->
                            DropdownMenuItem(
                                onClick = {
                                    onEvent(ExerciseEvent.SetCaloriesValue(gradeValue))
                                    isDropdownMenuVisible = false
                                }
                            ) {
                                Text(text = gradeValue.toString())
                            }
                        }
                    }
                }

            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    Log.d("HALO","KLIKNIĘTE")
                    onEvent(ExerciseEvent.SaveExercise)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}