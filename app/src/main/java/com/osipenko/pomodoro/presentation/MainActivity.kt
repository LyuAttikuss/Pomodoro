package com.osipenko.pomodoro.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osipenko.pomodoro.ui.theme.PomodoroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(innerPadding)
                }
            }
        }
    }
}

@Composable
private fun MainContent(innerPadding: PaddingValues) {
    val startDestination = "startTaskScreen"
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues = innerPadding)
    ) {
        composable("startTaskScreen") {
            StartTaskScreen(
                viewModel = hiltViewModel<StartTaskViewModel>(),
                navigateToTimerScreen = {
                    navController.navigate("timerScreen") {
                        launchSingleTop = true
                        popUpTo("startTaskScreen") {
                            inclusive = true
                            saveState = false
                        }
                    }
                }
            )
        }

        composable("timerScreen") {
            TimerScreen(viewModel = hiltViewModel<TimerViewModel>()) {
                navController.navigate("startTaskScreen") {
                    launchSingleTop = true
                    popUpTo("timerScreen") {
                        inclusive = true
                        saveState = false
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun addTaskItem(
//    onDismiss: () -> Unit,
//    onComplete: () -> Unit
//) {
//    val viewModel: AddTaskViewModel = hiltViewModel()
//    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()
//
//    val close = viewModel.state.collectAsStateWithLifecycle().value
//
//    if (close) {
//        LaunchedEffect(true) {
//            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                if (!sheetState.isVisible) {
//                    onComplete()
//                    onDismiss()
//                }
//            }
//        }
//    }
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = sheetState
//    ) {
//        var text by remember { mutableStateOf("") }
//        BasicTextField(
//            value = text,
//            onValueChange = { text = it },
//            modifier = Modifier
//                .padding(all = 16.dp)
//                .fillMaxWidth()
//                .height(100.dp)
//                .border(1.dp, Color.Gray)
//                .padding(8.dp),
//            singleLine = false,
//            minLines = 3,
//            maxLines = 10
//        )
//
//        Button(
//            enabled = text.isNotBlank(),
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .align(Alignment.End),
//            onClick = {
//                viewModel.add(text)
//            }
//        ) {
//            Text("Create task")
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBar() {
//    TopAppBar(
//        title = {
//            Text(text = "Pomodoro")
//        }
//    )
//}
//
//@Composable
//fun TaskListView(paddingValues: PaddingValues) {
//    val viewModel: TaskListViewModel = hiltViewModel()
//    val addViewModel: AddTaskViewModel = hiltViewModel()
//    val data = viewModel.state.collectAsStateWithLifecycle().value
//    val timerState = addViewModel.timerState.collectAsStateWithLifecycle().value
//
//    when (data.isNotEmpty()) {
//        true ->
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 70.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                Text(
//                    text = data.first(),
//                    modifier = Modifier.padding(24.dp),
//                    fontSize = 40.sp
//                )
//
//                TaskTimer(timerState.currentValue)
//
//                DotsIndicator(totalDots = 4, currentIndex = 2)
//
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//
//                    when {
//                        timerState.inProgress -> {
//                            StopButton {
//                                addViewModel.stopTimer()
//                            }
//                            PauseButton {
//                                addViewModel.pauseTimer()
//                            }
//                        }
//                        else -> {
//                            PlayButton {
//                                addViewModel.startTimer()
//                            }
//                        }
//                    }
//
//                    if (!timerState.isLast) {
//                        NextButton {
//                            addViewModel.nextPeriodTimer()
//                        }
//                    }
//                }
//            }
//
//        else -> {}
//    }
//}
//
//
//@Composable
//fun CardItem(text: String, modifier: Modifier = Modifier) {
//
//    Card(
//        colors = CardColors(
//            contentColor = colorResource(id = R.color.white),
//            containerColor = colorResource(id = R.color.purple_700),
//            disabledContentColor = colorResource(id = R.color.white),
//            disabledContainerColor = colorResource(id = R.color.purple_500)
//        ),
//        modifier = Modifier
//            .wrapContentHeight()
//            .fillMaxWidth()
//    ) {
//        Text(
//            text = text,
//            modifier = modifier
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTheme {
        CardItem("ToDO")
    }
}
