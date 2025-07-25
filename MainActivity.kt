package com.example.wonderschooldisney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wonderschooldisney.ui.MainMenuScreen
import com.example.wonderschooldisney.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainMenuScreen()
            }
        }
    }
}