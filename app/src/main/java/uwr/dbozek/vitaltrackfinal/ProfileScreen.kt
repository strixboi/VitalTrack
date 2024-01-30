package uwr.dbozek.vitaltrackfinal


import android.provider.Settings.Global
import android.util.Log
import androidx.collection.emptyLongLongMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uwr.dbozek.vitaltrackfinal.ui.theme.VitalTrackFinalTheme



@OptIn(ExperimentalMaterial3Api::class)
class userinfo(){
    var weight = GlobalVariables.user_weight
    var height = GlobalVariables.user_height
}


@Composable
fun ProfileScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

        Row(
            modifier = Modifier
                .padding()
                .fillMaxWidth()
        ){
            Avatar(imageId = 0)
            UserInfo(name = "Dawid", age = 21)
//            EditButton(onClick = {
//
//            })
        }
        Row(modifier = Modifier.padding(16.dp)){UserMetrics(weight = GlobalVariables.user_weight, height = GlobalVariables.user_height)
            BmiInfo(bmi = calculateBmi(GlobalVariables.user_weight,GlobalVariables.user_height))
            EditUserStats(onClick = {
                GlobalVariables.isEditingUserStats = true
                Log.d("HALO","KLIKAM")
            })}

//        treningi = addTraining(Training("2020-01-01",300,"sex"))
        TrainingHistory(trainings = treningi)




    }
}

@Composable
fun TrainingHistory(trainings: List<Training>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 24.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(trainings) { training ->
                TrainingCard(training = training)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingCard(training: Training) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = {
            // Obsługa kliknięcia na kartę treningu
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Data: ${training.date}",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Kalorie: ${training.caloriesBurned}",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Sport: ${training.sport}",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}




@Composable
fun Avatar(imageId: Int? = null) {
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "Avatar",
        tint = Color.Red,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}

@Composable
fun UserInfo(name: String, age: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = name,
            color = Color.Red,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$age lat",
            color = Color.Red,
            fontSize = 16.sp
        )
    }
}

@Composable
fun UserMetrics(weight: Float, height: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Waga: $weight kg",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
        Text(
            text = "Wzrost: $height cm",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
    }
}

@Composable
fun BmiInfo(bmi: Float) {
    Text(
        text = "BMI: %.1f".format(bmi),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 16.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun EditButton(onClick: () -> Unit) {
    Box(modifier = Modifier.padding(16.dp)){
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color.Red,
            modifier = Modifier
                .size(28.dp)
                .padding(vertical = 0.dp)
                .clickable { onClick() }
        )}
}
@Composable
fun EditUserStats(onClick: () -> Unit) {
    Box(modifier = Modifier.padding(16.dp)) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Edit",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(28.dp)
                .padding(vertical = 0.dp)
                .clickable { onClick() }
        )
        // Obsługa otwarcia okna dialogowego po kliknięciu przycisku
        if (GlobalVariables.isEditingUserStats) {
            var editedWeight by remember { mutableStateOf(GlobalVariables.user_weight.toString()) }
            var editedHeight by remember { mutableStateOf(GlobalVariables.user_height.toString()) }

            AlertDialog(
                onDismissRequest = { GlobalVariables.isEditingUserStats = false },
                title = { Text(text = "Edytuj metrykę") },
                text = {
                    // Dodaj formularz edycji danych
                    Column {
                        Text(text = "Waga:")
                        // Pole do edycji wagi
                        TextField(
                            value = editedWeight,
                            onValueChange = { editedWeight = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(text = "Wzrost:")
                        // Pole do edycji wzrostu
                        TextField(
                            value = editedHeight,
                            onValueChange = { editedHeight = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    // Przycisk potwierdzający zmiany
                    Button(onClick = {
                        // Dokonaj aktualizacji zmiennych globalnych
                        val newWeight = editedWeight.toFloatOrNull()
                        val newHeight = editedHeight.toIntOrNull()

                        if (newWeight != null && newHeight != null) {
                            GlobalVariables.user_weight = newWeight
                            GlobalVariables.user_height = newHeight
                        } else {
                            // Jeśli konwersja nie powiodła się, obsłuż błąd lub dostarcz informacje o błędzie
                            // Możesz także wyświetlić komunikat do użytkownika
                            Log.e("EditUserStats", "Błąd konwersji danych")
                        }

                        GlobalVariables.isEditingUserStats = false
                    }) {
                        Text(text = "Zapisz")
                    }
                }
            )
        }
    }
}


// Funkcja do wyliczania BMI
fun calculateBmi(weight: Float, height: Int): Float {
    val heightInMeters = height / 100.0
    return (weight / (heightInMeters * heightInMeters)).toFloat()
}








@Preview
@Composable
fun ProfileScreenPreview() {
    VitalTrackFinalTheme {
        ProfileScreen()
    }
}