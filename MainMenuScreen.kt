package com.example.wonderschooldisney.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.example.wonderschooldisney.MathActivity

@Composable
fun MainMenuScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE1F5FE))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Волшебная Школа Disney",
            fontSize = 32.sp,
            color = Color(0xFF1565C0),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        SubjectButton("🔢 Математика") {
            context.startActivity(Intent(context, MathActivity::class.java))
        }
        SubjectButton("📖 Чтение") { /* Заглушка */ }
        SubjectButton("🎨 Рисование") { /* Заглушка */ }
        SubjectButton("🔤 Русский язык") { }
        SubjectButton("🌍 Окружающий мир") { }
    }
}

@Composable
fun SubjectButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
    ) {
        Text(text, fontSize = 20.sp, color = Color.White)
    }
}