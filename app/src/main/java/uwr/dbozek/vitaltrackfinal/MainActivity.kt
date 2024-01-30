package uwr.dbozek.vitaltrackfinal

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import uwr.dbozek.vitaltrackfinal.db.Exercise
import uwr.dbozek.vitaltrackfinal.db.ExerciseDao
import uwr.dbozek.vitaltrackfinal.db.ExerciseDatabase
import uwr.dbozek.vitaltrackfinal.db.ExerciseViewModel
import uwr.dbozek.vitaltrackfinal.ui.theme.VitalTrackFinalTheme
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import uwr.dbozek.vitaltrackfinal.ui.theme.VitalTrackFinalTheme


class MainActivity : ComponentActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitalTrackFinalTheme {
                window.statusBarColor = getColor(R.color.black)
               Screen()

            }


        }


    }
}

