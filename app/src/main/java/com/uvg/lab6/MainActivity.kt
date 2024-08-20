package com.uvg.lab6
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedCounter()
        }
    }
}

@Composable
fun AdvancedCounter() {
    var count by remember { mutableStateOf(0) }
    var increments by remember { mutableStateOf(0) }
    var decrements by remember { mutableStateOf(0) }
    var maxValue by remember { mutableStateOf(0) }
    var minValue by remember { mutableStateOf(0) }
    var totalChanges by remember { mutableStateOf(0) }
    val history = remember { mutableStateListOf<Int>() }

    fun updateStatistics() {
        totalChanges++
        maxValue = maxOf(maxValue, count)
        minValue = minOf(minValue, count)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Samuel Mejía", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                count--
                decrements++
                history.add(count)
                updateStatistics()
            }) {
                Text(text = "-")
            }

            Text(text = count.toString(), fontSize = 48.sp, modifier = Modifier.padding(16.dp))

            Button(onClick = {
                count++
                increments++
                history.add(count)
                updateStatistics()
            }) {
                Text(text = "+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        StatisticsSection(increments, decrements, maxValue, minValue, totalChanges)

        Spacer(modifier = Modifier.height(16.dp))

        HistorySection(history)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            count = 0
            increments = 0
            decrements = 0
            maxValue = 0
            minValue = 0
            totalChanges = 0
            history.clear()
        }) {
            Text(text = "Reiniciar")
        }
    }
}

@Composable
fun StatisticsSection(
    increments: Int,
    decrements: Int,
    maxValue: Int,
    minValue: Int,
    totalChanges: Int
) {
    Column {
        Text(text = "Total Incrementos: $increments")
        Text(text = "Total Decrementos: $decrements")
        Text(text = "Valor Máximo: $maxValue")
        Text(text = "Valor Mínimo: $minValue")
        Text(text = "Total Cambios: $totalChanges")
    }
}

@Composable
fun HistorySection(history: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.height(150.dp)
    ) {
        items(history.size) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(50.dp)
                    .background(if (history[index] >= 0) Color.Green else Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(text = history[index].toString(), color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AdvancedCounter()
}
