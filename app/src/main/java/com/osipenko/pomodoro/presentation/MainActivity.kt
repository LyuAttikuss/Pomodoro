package com.osipenko.pomodoro.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.osipenko.pomodoro.R
import com.osipenko.pomodoro.ui.theme.PomodoroTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AddTaskViewModel = hiltViewModel()
            val timerValue = viewModel.timerValue.collectAsStateWithLifecycle().value

            PomodoroTheme {
                var showBottomSheet by remember { mutableStateOf(false) }
                Scaffold(
                    topBar = { TopBar() },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text("Add task") },
                            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                            onClick = {
                                showBottomSheet = true
                                //onFinish()
                            }
                        )
                    }
                ) { contentPadding ->
                    TaskListView(contentPadding)

                    if (showBottomSheet) {
                        addTaskItem(
                            onDismiss = {
                                showBottomSheet = false
                            },
                            onComplete = {
                                viewModel.startTimer()
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addTaskItem(
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    val viewModel: AddTaskViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val close = viewModel.state.collectAsStateWithLifecycle().value

    if (close) {
        LaunchedEffect(true) {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onComplete()
                    onDismiss()
                }
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        var text by remember { mutableStateOf("") }
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .height(100.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp),
            singleLine = false,
            minLines = 3,
            maxLines = 10
        )

        Button(
            enabled = text.isNotBlank(),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.End),
            onClick = {
                viewModel.add(text)
            }
        ) {
            Text("Create task")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Pomodoro")
        }
    )
}

@Composable
fun TaskListView(paddingValues: PaddingValues) {
    val viewModel: TaskListViewModel = hiltViewModel()
    val addViewModel: AddTaskViewModel = hiltViewModel()
    val data = viewModel.state.collectAsStateWithLifecycle().value
    val timerValue = addViewModel.timerValue.collectAsStateWithLifecycle().value

    when (data.isNotEmpty()) {
        true ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = data.first(),
                    modifier = Modifier.padding(24.dp),
                    fontSize = 40.sp
                )

                TaskTimer(timerValue)

                DotsIndicator(totalDots = 4, currentIndex = 2)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PlayButton {
                        addViewModel.startTimer()
                    }

                    StopButton {
                        addViewModel.stopTimer()
                    }

                    PauseButton {
                        addViewModel.pauseTimer()
                    }

                    NextButton {
                        addViewModel.nextPeriodTimer()
                    }
                }
            }

        else -> {}
    }
}

@Composable
fun PlayButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.icon_play),
                contentDescription = "Stop",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun StopButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.icon_stop),
                contentDescription = "Stop",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.icon_play_next),
                contentDescription = "Stop",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun PauseButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.icon_pause),
                contentDescription = "Stop",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun CardItem(text: String, modifier: Modifier = Modifier) {

    Card(
        colors = CardColors(
            contentColor = colorResource(id = R.color.white),
            containerColor = colorResource(id = R.color.purple_700),
            disabledContentColor = colorResource(id = R.color.white),
            disabledContainerColor = colorResource(id = R.color.purple_500)
        ),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = modifier
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(totalDots) { index ->
            val backgroundColor = when {
                currentIndex == index -> Color.Magenta
                index < currentIndex -> Color.Black
                else -> Color.Gray
            }
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
            )
            if (index < totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTheme {
        CardItem("ToDO")
    }
}
