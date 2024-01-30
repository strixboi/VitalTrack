package uwr.dbozek.vitaltrackfinal


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uwr.dbozek.vitaltrackfinal.ui.theme.VitalTrackFinalTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import java.util.Date
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val newWorkout by remember { mutableStateOf("") }
    var panel by remember { mutableStateOf(0)}

    val currentDate = remember {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        dateFormat.format(Calendar.getInstance().time)
    }

    val currentTime by rememberUpdatedState(System.currentTimeMillis())

    val timeString = remember(currentTime) {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        dateFormat.format(currentTime)
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        GreetingWithDate(currentDate = currentDate,currentTime = timeString)

        val lastTraining: Training = treningi.last()
        val lastExerciseName: String = lastTraining.sport
        val lastExerciseCalories: Float = lastTraining.caloriesBurned.toFloat()
        val lastExerciseDate: String = lastTraining.date

        Text( text = "Ostatni trening")

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
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Data: ${lastExerciseDate}",
                    fontSize = 16.sp,
                    color = Color.Black,
                )
                Text(
                    text = "Kalorie: ${lastExerciseCalories}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Sport: ${lastExerciseName}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }



        when(panel){
            0 -> LineChartScreen()
            1 -> PieChartDraw()
            2 -> CaloriesChartScreen()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RadioButton(
                selected = panel == 0,
                onClick = { panel = 0 },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red
                ),
            )
            RadioButton(
                selected = panel == 1,
                onClick = { panel = 1 },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red
                )
            )
            RadioButton(
                selected = panel == 2,
                onClick = { panel = 2 },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red
                )
            )
        }

    }
}



@Composable
fun GreetingWithDate(currentDate: String,currentTime: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Cześć Dawid, mamy",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp), // Zmieniono fontSize na większy
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = currentDate,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp), // Zmieniono fontSize na większy
            color = Color.Red
        )
        Text(
            text = currentTime,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp), // Zmieniono fontSize na większy
            color = Color.Red
        )
    }
}





@Preview
@Composable
fun WorkoutsScreenPreview() {
    VitalTrackFinalTheme {
        HomeScreen()
    }

}

@Composable
fun PieChartDraw(){



val totalcalories = treningi.sumBy { it.caloriesBurned }

    val lastTraining: Training = treningi.last()
    val lastExerciseName: String = lastTraining.sport

    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                label = "Pływanie",
                value = 30f,
                color = Color(0xFFE41818)
            ),
            PieChartData.Slice(
                label = "Rower",
                value = 10f,
                color = Color(0xFFE97171)
            ),
            PieChartData.Slice(
                label = "Wspinaczka",
                value = 10f,
                color = Color(0xFFAE4545)
            ),
            PieChartData.Slice(
                label = "Bieganie",
                value = 10f,
                color = Color(0xFFF2421F)
            ),
            PieChartData.Slice(
                label = lastExerciseName,
                value = 10f,
                color = Color.Green
            )
        ),
        plotType = PlotType.Pie
    )
    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        activeSliceAlpha = 0.5f,
        animationDuration = 600
    )
    Text(text = "Ulubione ćwiczenia")

    PieChart(modifier = Modifier
        .width(300.dp)
        .height(300.dp), pieChartData = pieChartData, pieChartConfig = pieChartConfig)

}

@Composable
fun DonutChartDraw(){
    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                label = "Pływanie",
                value = 65f,
                color = Color(0xFFE41818)
            ),
            PieChartData.Slice(
                label = "Rower",
                value = 40f,
                color = Color(0xFFE97171)
            ),
            PieChartData.Slice(
                label = "Piłka nożna",
                value = 65f,
                color = Color(0xFFAE4545)
            ),
            PieChartData.Slice(
                label = "Siatkówka",
                value = 65f,
                color = Color(0xFFF2421F)
            )
        ),
        plotType = PlotType.Donut
    )
    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,

        activeSliceAlpha = 0.5f,
        animationDuration = 600,
    )

    PieChart(modifier = Modifier
        .width(400.dp)
        .height(400.dp), pieChartData = pieChartData, pieChartConfig = pieChartConfig)

}

@Composable
fun LineChartScreen(){
    val steps = 5
    val pointsData = listOf(
        Point(0f,99f),
        Point(1f,98.5f),
        Point(2f,95.5f),
        Point(3f,93.4f),
        Point(4f,90f),
        Point(5f,95.5f),
        Point(6f,GlobalVariables.user_weight),
    )


    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)

        .axisLineColor(Color.Red)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData{steps ->
            val yScale = 2 * steps + 90
            (yScale).toString()
        }
        .axisLineColor(Color.Red)
        .axisLabelColor(Color.Red)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = Color.Red,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),

        backgroundColor = MaterialTheme.colorScheme.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outline),
    )
    Text(text = "Historia twojej wagi")

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )

}


@Composable
fun CaloriesChartScreen(){
    val steps = 5
    val sumOfcalories = treningi.sumBy { it.caloriesBurned }.toFloat()
    val pointsData = listOf(
        Point(0f,200f),
        Point(1f,500f),
        Point(2f,750f),
        Point(3f,1200f),
        Point(4f,1500f),
        Point(5f,1800f),
        Point(6f,sumOfcalories),
    )


    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)

        .axisLineColor(Color.Red)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData{i ->
            val yScale = sumOfcalories / steps
            (i * yScale).toString()
        }
        .axisLineColor(Color.Red)
        .axisLabelColor(Color.Red)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = Color.Red,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),

        backgroundColor = MaterialTheme.colorScheme.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outline),
    )
    Text(text = "Spalone kalorie")

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )







}