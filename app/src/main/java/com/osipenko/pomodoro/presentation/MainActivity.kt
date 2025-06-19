package com.osipenko.pomodoro.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osipenko.pomodoro.R
import com.osipenko.pomodoro.ui.theme.PomodoroTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroTheme {
                val sheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()
                var showBottomSheet by remember { mutableStateOf(false) }
                Scaffold(
                    topBar = { TopBar() },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text("Add task") },
                            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                            onClick = {
                                showBottomSheet = true
                            }
                        )
                    }
                ) { contentPadding ->
                    TaskListView(contentPadding)

                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            },
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
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                }
                            ) {
                                Text("Create task")
                            }
                        }
                    }
                }


//                Column(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    verticalArrangement = Arrangement.SpaceBetween
//                ) {
//                    TopAppBar(
//                        title = {
//                            Text(text = "Pomodoro")
//                        }
//                    )
//
//                    Column(
//                        modifier = Modifier
//                            .verticalScroll(rememberScrollState())
//                            .weight(1f, false)
//                            .padding(24.dp)
//                    ) {
//                        CardItem(
//                            text = "Android task",
//                            modifier = Modifier
//                                .padding(24.dp)
//                                .testTag("taskItemText")
//                        )
//                    }

//                    Button(
//                        onClick = { },
//                        modifier = Modifier
//                            .padding(horizontal = 24.dp)
//                            .padding(bottom = 72.dp, top = 24.dp)
//                            .fillMaxWidth()
//                            .testTag("addTaskButton")
//                    ) {
//                        Text(
//                            text = "Add task"
//                        )
//                    }

                    //addTaskBottomSheet()
               // }
            }
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
