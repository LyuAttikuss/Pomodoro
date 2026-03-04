package com.osipenko.pomodoro.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.osipenko.pomodoro.R

@Composable
fun StartTaskScreen(
    viewModel: StartTaskViewModel,
    navigateToTimerScreen: () -> Unit
) {
    val input = rememberSaveable { mutableStateOf("") }

    StartTaskScreenUi(
        input = input.value,
        onInputChange = { text ->
            input.value = text
        },
        onStartTaskClick = {
            navigateToTimerScreen.invoke()
        }
    )
}

@Composable
fun StartTaskScreenUi(
    input: String,
    onInputChange: (String) -> Unit,
    onStartTaskClick: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.task_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("taskInputField"),
            value = input,
            onValueChange = onInputChange,
        )
        Button(
            onClick = {
                onStartTaskClick.invoke(input)
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = input.isNotBlank()
        ) {
            Text(text = stringResource(R.string.start))
        }
    }
}