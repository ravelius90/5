package com.example.wonderschooldisney.ui

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint as AndroidPaint
import android.os.Environment
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

@Composable
fun DrawingScreen() {
    val context = LocalContext.current
    val paths = remember { mutableStateListOf<Triple<Path, Color, Float>>() }
    var currentPath = remember { Path() }
    var currentColor by remember { mutableStateOf(Color(0xFF3F51B5)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf(Color.Black, Color.Red, Color.Blue, Color.Green, Color.Yellow).forEach { color ->
                Button(
                    onClick = { currentColor = color },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = color)
                ) {}
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.weight(1f)) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPath.moveTo(offset.x, offset.y)
                        },
                        onDrag = { _, dragAmount ->
                            currentPath.lineTo(
                                currentPath.getLastPoint().x + dragAmount.x,
                                currentPath.getLastPoint().y + dragAmount.y
                            )
                        },
                        onDragEnd = {
                            paths.add(Triple(currentPath, currentColor, 8f))
                            currentPath = Path()
                        }
                    )
                }
            ) {
                paths.forEach { (path, color, strokeWidth) ->
                    drawPath(path, color, style = Stroke(width = strokeWidth))
                }
                drawPath(currentPath, currentColor, style = Stroke(width = 8f))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            saveDrawingToGallery(paths, context)
        }) {
            Text("Сохранить в галерею")
        }
    }
}

fun saveDrawingToGallery(paths: List<Triple<Path, Color, Float>>, context: android.content.Context) {
    val bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
    val canvas = AndroidCanvas(bitmap)
    canvas.drawColor(AndroidColor.WHITE)
    val paint = AndroidPaint().apply {
        style = AndroidPaint.Style.STROKE
    }
    for ((path, color, strokeWidth) in paths) {
        paint.color = color.toArgb()
        paint.strokeWidth = strokeWidth
        canvas.drawPath(path.asAndroidPath(), paint)
    }
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "drawing_${System.currentTimeMillis()}.png")
    val stream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.flush()
    stream.close()
}