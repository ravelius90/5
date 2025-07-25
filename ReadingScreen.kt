package com.example.wonderschooldisney.ui

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderschooldisney.R

@Composable
fun ReadingScreen() {
    val context = LocalContext.current

    val language = remember { mutableStateOf("ru") } // "en" or "ru"

    val tasks = listOf(
        ReadingTask(R.drawable.cat, "кот", "cat", listOf("кот", "пёс", "дом", "нос"), listOf("cat", "dog", "house", "nose"), R.raw.cat_audio, R.raw.cat_audio_en),
        ReadingTask(R.drawable.dog, "пёс", "dog", listOf("кот", "пёс", "мост", "рот"), listOf("cat", "dog", "bridge", "mouth"), R.raw.dog_audio, R.raw.dog_audio_en),
        ReadingTask(R.drawable.house, "дом", "house", listOf("дом", "кот", "лес", "мяч"), listOf("house", "cat", "forest", "ball"), R.raw.house_audio, R.raw.house_audio_en)
    )

    var taskIndex by remember { mutableStateOf(0) }
    val task = tasks[taskIndex % tasks.size]

    val options = if (language.value == "ru") task.optionsRu else task.optionsEn
    val correct = if (language.value == "ru") task.wordRu else task.wordEn
    val audio = if (language.value == "ru") task.audioRu else task.audioEn

    var selected by remember { mutableStateOf<String?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("Язык:", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { language.value = "ru" }) { Text("Русский") }
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = { language.value = "en" }) { Text("English") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(if (language.value == "ru") "Чтение" else "Reading", fontSize = 28.sp, color = Color(0xFF4A148C))
        Spacer(modifier = Modifier.height(16.dp))
        Image(painter = painterResource(id = task.imageRes), contentDescription = null)
        Spacer(modifier = Modifier.height(20.dp))
        options.forEach { option ->
            Button(
                onClick = {
                    selected = option
                    isCorrect = option == correct
                    if (isCorrect == true) {
                        MediaPlayer.create(context, audio).start()
                    }
                },
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
            ) {
                Text(option, fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        isCorrect?.let {
            Text(
                if (it) if (language.value == "ru") "Правильно!" else "Correct!" else if (language.value == "ru") "Нет, это не то слово." else "Nope, try again.",
                color = if (it) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            taskIndex++
            selected = null
            isCorrect = null
        }) {
            Text(if (language.value == "ru") "Следующее задание" else "Next Task")
        }
    }
}

data class ReadingTask(
    val imageRes: Int,
    val wordRu: String,
    val wordEn: String,
    val optionsRu: List<String>,
    val optionsEn: List<String>,
    val audioRu: Int,
    val audioEn: Int
)