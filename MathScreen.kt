package com.example.wonderschooldisney.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import kotlin.random.Random

sealed class MathTask {
    data class Equation(val a: Int, val b: Int) : MathTask()
    data class Compare(val left: Int, val right: Int) : MathTask()
    data class Addition(val a: Int, val b: Int) : MathTask()
}

@Composable
fun MathScreen() {
    val task = remember { generateTask() }
    val correctAnswer = when (task) {
        is MathTask.Equation -> task.b - task.a
        is MathTask.Compare -> if (task.left > task.right) 1 else if (task.left < task.right) -1 else 0
        is MathTask.Addition -> task.a + task.b
    }

    val options = remember { generateOptions(correctAnswer) }
    var selected by remember { mutableStateOf<Int?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDE7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Математика", fontSize = 24.sp, color = Color(0xFF6A1B9A))
        Spacer(modifier = Modifier.height(16.dp))

        when (task) {
            is MathTask.Equation -> Text("x + ${task.a} = ${task.b}", fontSize = 32.sp)
            is MathTask.Compare -> Text("${task.left} ? ${task.right}", fontSize = 32.sp)
            is MathTask.Addition -> Text("${task.a} + ${task.b} = ?", fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            options.forEach { option ->
                AnswerOption(
                    number = option,
                    isSelected = selected == option,
                    isCorrect = isCorrect,
                    correctAnswer = correctAnswer,
                    onClick = {
                        selected = option
                        isCorrect = option == correctAnswer
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        isCorrect?.let {
            Text(
                if (it) "Молодец!" else "Попробуй снова",
                fontSize = 20.sp,
                color = if (it) Color(0xFF2E7D32) else Color(0xFFD32F2F)
            )
        }
    }
}

fun generateTask(): MathTask {
    return when (Random.nextInt(3)) {
        0 -> MathTask.Equation(Random.nextInt(1, 20), Random.nextInt(21, 40))
        1 -> MathTask.Compare(Random.nextInt(1, 20), Random.nextInt(1, 20))
        else -> MathTask.Addition(Random.nextInt(1, 15), Random.nextInt(1, 15))
    }
}

fun generateOptions(correct: Int): List<Int> {
    val set = mutableSetOf(correct)
    while (set.size < 4) set.add(correct + Random.nextInt(-3, 4))
    return set.shuffled()
}