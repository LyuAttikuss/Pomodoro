package com.osipenko.pomodoro.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.layout.Column
import androidx.glance.text.Text
import java.io.Serializable

@Composable
fun TimerScreen(
    viewModel: TimerViewModel
) {
    val timerState = viewModel.state.collectAsStateWithLifeCycle()

    TimerScreenUi(
        inProgress = timerState.inProgress,
        onStopTimerClick = {},
        onStartTimerClick = {},
        onPauseTimerClick = {},
        onPlayTimerClick = {}
    )
}

@Composable
fun TimerScreenUi(
    inProgress: Any,
    onStopTimerClick: () -> Unit,
    onStartTimerClick: () -> Unit,
    onPauseTimerClick: () -> Unit,
    onPlayTimerClick: () -> Unit
) {
    Column {
        Button(
            onClick = {
                onStartTimerClick.invoke()
            }
        ) {
            Text(
                text = "Start",
                modifier = Modifier.testTag("startTimerButton")
            )
        }
    }
}

interface TimerUi : Serializable {

    @Composable
    fun Show(onPlay: () -> Unit, onStop: () -> Unit, onPause: () -> Unit)

    data object Start : TimerUi {
        private fun readResolve(): Any = Start

        @Composable
        override fun Show(onPlay: () -> Unit, onStop: () -> Unit, onPause: () -> Unit) = Unit
    }

    data class InProgress(private val timerState: TimerState) : TimerUi {

        @Composable
        override fun Show(onPlay: () -> Unit, onStop: () -> Unit, onPause: () -> Unit) {
            Column {
                Text(
                    text = timerState.currentValue,
                    modifier = Modifier.padding(24.dp),
                    fontSize = 20.sp
                )

                TimerDotsIndicator(totalDots = 4, currentIndex = timerState.currentWorkIndex)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    if (timerState.inProgress) {
                        StopButton {
                            onStop.invoke()
                        }
                        PauseButton {
                            onPause.invoke()
                        }
                        PlayButton {
                            onPlay.invoke()
                        }
                    }
                    else {
                        //TODO: show error
                    }
                }
            }
        }

    }
}

@Composable
fun TimerDotsIndicator(
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