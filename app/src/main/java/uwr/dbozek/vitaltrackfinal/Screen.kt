package uwr.dbozek.vitaltrackfinal


import android.graphics.Paint.Align
import android.graphics.drawable.Icon
import android.os.WorkSource
import android.provider.ContactsContract.ProfileSyncState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uwr.dbozek.vitaltrackfinal.db.ExerciseEvent
import uwr.dbozek.vitaltrackfinal.db.ExerciseState


@Composable
fun Screen(
) {
    val selectedItem = rememberSaveable { mutableStateOf(0) }


    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {


        BottomNavigation(
            backgroundColor = Color.Red,
        ) {
            BottomNavigationItem(
                selected = selectedItem.value == 0,
                onClick = { selectedItem.value = 0 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = if (selectedItem.value == 0) Color.White else Color.Black,
                        modifier = Modifier.padding(4.dp)
                    )
                },
                label = {
                    Text(
                        text = "Home",
                        color = if (selectedItem.value == 0) Color.White else Color.Black,
                    )
                }
            )

            BottomNavigationItem(
                selected = selectedItem.value == 1,
                onClick = { selectedItem.value = 1 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Workouts",
                        tint = if (selectedItem.value == 1) Color.White else Color.Black,
                        modifier = Modifier.padding(4.dp)
                    )
                },
                label = {
                    Text(
                        text = "Workouts",
                        color = if (selectedItem.value == 1) Color.White else Color.Black,
                    )
                }
            )

            BottomNavigationItem(
                selected = selectedItem.value == 2,
                onClick = { selectedItem.value = 2 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = if (selectedItem.value == 2) Color.White else Color.Black,
                        modifier = Modifier.padding(4.dp)
                    )
                },
                label = {
                    Text(
                        text = "Profile",
                        color = if (selectedItem.value == 2) Color.White else Color.Black,
                    )
                }
            )
        }


    }
    when (selectedItem.value) {
        0 -> HomeScreen()
        1 -> WorkoutsScreen()
        2 -> ProfileScreen()
    }
}
