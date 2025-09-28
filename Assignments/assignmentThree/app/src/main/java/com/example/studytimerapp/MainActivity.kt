package com.example.studytimerapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * This project covers concepts from Chapter 6 lessons:
 * - "Understanding State in Compose" - for state management and updates
 * - "Stateless and Stateful Composables" - for component design patterns
 * - "Launched Effect" - for side effects and timers
 *
 * Students should review these lessons before starting:
 * - Understanding State in Compose lesson for state management
 * - Stateless and Stateful Composables lesson for component patterns
 * - Launched Effect lesson for side effects and timers
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudyTimerApp()
            }
        }
    }
}

@Composable
fun StudyTimerApp() {
    // TODO: Create state variables for the timer
    var isRunning by remember { mutableStateOf(false) } //tracking if active
    var sessionLength by remember { mutableStateOf(25) }
    var timeRemaining by remember { mutableStateOf(sessionLength * 60) } //holds countdown value
    var completedSessions by remember { mutableStateOf(0) }

    //Start/Reset button logic:
    val onToggleTimer = {
        if (isRunning) {
            isRunning = false
            timeRemaining = sessionLength * 60
        } else {
            isRunning = true
            timeRemaining = sessionLength * 60
        }
    }

    val onSessionLengthChange = { newLength: Int ->
        sessionLength = newLength
        timeRemaining = newLength * 60
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Call the TimerDisplay composable here
        TimerDisplay(timeRemaining, sessionLength)

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Call the TimerControls composable here

        TimerControls(isRunning, onToggleTimer) //red because func body is not completed yet

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Call the SessionSettings composable here
        SessionSettings(sessionLength, onSessionLengthChange) //red because func body is not completed yet

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Display the completed sessions count
        Text(
            text = "Completed Sessions:$completedSessions"
        )
    }

    // TODO: Use LaunchedEffect to create the countdown timer
    LaunchedEffect(isRunning) {
        while (isRunning && timeRemaining > 0 ) { //ensuring that time is running and factors reaches zero, and only running when it should
            delay(1000L) //expected L
            timeRemaining -= 1 //decrease value by 1
        }

        //the completed session code:
        if (isRunning && timeRemaining == 0 ){ //resetting when done
            completedSessions += 1
            isRunning = false
        }
    }
}

@Composable
fun TimerDisplay(
    timeRemaining: Int,
    sessionLength: Int
) {
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val progress = 1f - (timeRemaining.toFloat() / (sessionLength * 60))
    val percentComplete = (progress * 100).toInt()

    // TODO: Create a stateless timer display component
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Study Timer",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = String.format("%02d:%02d",minutes,seconds),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$percentComplete% Complete",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun TimerControls(
    isRunning: Boolean,
    onToggleTimer: () -> Unit

) {
    // TODO: Create stateful timer control button
    Button(onClick = onToggleTimer) {
        Text(
            text = if (isRunning)"Reset" else "Start" //if isRunning is false start, if true reset
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudyTimerPreview() {
    StudyTimerApp()
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionSettings(
    sessionLength: Int,
    onSessionLengthChange: (Int) -> Unit
) {
    Column (modifier = Modifier.padding(16.dp,)) {
        Text(text = "Session Length: ${sessionLength} minutes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center

        ) //displays current session

        FlowRow(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            listOf(5, 15, 25, 45).forEach { length ->
                val isSelected = length == sessionLength
                Button(
                    onClick = { onSessionLengthChange(length) },
                    modifier = Modifier
                        .width(70.dp)
                        .height(48.dp)
                        .padding(end = 8.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor =  if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = "${length}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    }
                }
            }
        }
    }


