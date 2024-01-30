package uwr.dbozek.vitaltrackfinal


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object GlobalVariables {
    var user_weight by mutableFloatStateOf(95f)
    var user_height by mutableIntStateOf(196)
    var isEditingUserStats by mutableStateOf(false)
    var user_name by mutableStateOf("Dawid")
    var user_age by mutableIntStateOf(21)


}