package com.osipenko.pomodoro.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osipenko.pomodoro.R
import com.osipenko.pomodoro.ui.theme.PomodoroTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TopAppBar(
                        title = {
                            Text(text = "Pomodoro")
                        }
                    )

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f, false)
                            .padding(24.dp)
                    ) {
                        CardItem(
                            text = "Android task",
                            modifier = Modifier
                                .padding(24.dp)
                                .testTag("taskItemText")
                        )
                    }

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 72.dp, top = 24.dp)
                            .fillMaxWidth()
                            .testTag("addTaskButton")
                    ) {
                        Text(
                            text = "Add task"
                        )
                    }
                }
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTheme {
        CardItem("ToDO")
    }
}
