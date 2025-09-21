package com.example.interactiveButtonGrid


import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.ExperimentalLayoutApi


/**
 * This project covers concepts from Chapter 5 lessons:
 * - "Flow Rows and Columns" - for creating flexible grid layouts
 * - "Adding Space" - for spacing and arrangement in layouts
 * - "Adding Modifiers" - for layout modifiers and styling
 * - "Custom Modifiers" - for creating reusable modifier patterns
 * - "Click Events" - for handling user interactions
 *
 * Students should review these lessons before starting:
 * - Flow Rows and Columns lesson for grid layout implementation
 * - Adding Space lesson for spacing and arrangement
 * - Adding Modifiers lesson for layout modifiers
 * - Custom Modifiers lesson for modifier patterns
 * - Click Events lesson for user interaction handling
 */

// Data class to hold button information - already provided
data class ButtonData(
    val color: Color,
    val number: String
)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveButtonGrid() {
    var selectedButtons by remember { mutableStateOf(setOf<Int>()) } //ensure state survives recomp - initializes an empty immutable set
// TODO: Create a state variable to track which buttons are selected
    // List of button data (color, number) - already provided
    val buttonData = listOf(
        ButtonData(Color(0xFFE57373), "1"), // Red
        ButtonData(Color(0xFF81C784), "2"), // Green
        ButtonData(Color(0xFF64B5F6), "3"), // Blue
        ButtonData(Color(0xFFFFB74D), "4"), // Orange
        ButtonData(Color(0xFFBA68C8), "5"), // Purple
        ButtonData(Color(0xFF4DB6AC), "6"), // Teal
        ButtonData(Color(0xFFFF8A65), "7"), // Deep Orange
        ButtonData(Color(0xFF90A4AE), "8"), // Blue Grey
        ButtonData(Color(0xFFF06292), "9"), // Pink
        ButtonData(Color(0xFF7986CB), "10"), // Indigo
        ButtonData(Color(0xFF4DD0E1), "11"), // Cyan
        ButtonData(Color(0xFFFFD54F), "12"), // Yellow
        ButtonData(Color(0xFF8D6E63), "13"), // Brown
        ButtonData(Color(0xFF9575CD), "14"), // Deep Purple
        ButtonData(Color(0xFF4FC3F7), "15"), // Light Blue
        ButtonData(Color(0xFF66BB6A), "16"), // Light Green
        ButtonData(Color(0xFFFFCC02), "17"), // Amber
        ButtonData(Color(0xFFEC407A), "18"), // Pink
        ButtonData(Color(0xFF42A5F5), "19"), // Blue
        ButtonData(Color(0xFF26A69A), "20"), // Teal
        ButtonData(Color(0xFFFF7043), "21"), // Deep Orange
        ButtonData(Color(0xFF9CCC65), "22"), // Light Green
        ButtonData(Color(0xFF26C6DA), "23"), // Cyan
        ButtonData(Color(0xFFD4E157), "24")  // Lime
    )

    Column(
        // TODO: Add modifiers to make the Column fill the screen and add padding
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Create the title text
        Text(
            text = "Interactive Button Grid",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        // TODO: Create the selection count text
        Text(
            text = "Selected: ${selectedButtons.size} of ${buttonData.size}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // TODO: Create the FlowRow for the button grid
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // TODO: Inside the FlowRow, create buttons using forEachIndexed
            buttonData.forEachIndexed { index, button ->
                InteractiveButton(
                    buttonData = button,
                    isSelected = selectedButtons.contains(index), //checks if current button is part of selection
                    onClick = {
                        selectedButtons =
                            if (selectedButtons.contains(index)) { //checks if button is already selected
                                selectedButtons - index //deselects the button
                            } else {
                                selectedButtons + index //selects the button
                            }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TODO: Create the Clear Selection button
        Button(
            onClick = { selectedButtons = setOf() },
            enabled = selectedButtons.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Selection")
        }
    }
}

@Composable
fun InteractiveButton(
    buttonData: ButtonData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        // TODO: Add all the necessary modifiers:
        modifier = Modifier
            .size(80.dp)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer else buttonData.color,
                shape = MaterialTheme.shapes.medium
            ) //if the button is selected change the color, if not use button data
            .border(
                width = if (isSelected) 3.dp else 1.dp,//if selected the border is thicker if not its thinner
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(
                    alpha = 0.3f
                ),
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            // TODO: Set the text color based on selection state
            text = buttonData.number,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                MaterialTheme {
                    // TODO: Call the main composable function here
                    InteractiveButtonGrid()
                }
            }
        }
    }
}
    /**
     * Preview for Android Studio's design view.
     */
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun InteractiveButtonGridPreview() {
            InteractiveButtonGrid()
    }



