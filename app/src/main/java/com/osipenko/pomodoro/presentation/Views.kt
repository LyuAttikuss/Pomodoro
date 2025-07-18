package com.osipenko.pomodoro.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskTimer(value: String) {
    Text(
        text = value,
        modifier = Modifier.padding(24.dp),
        fontSize = 20.sp
    )
}