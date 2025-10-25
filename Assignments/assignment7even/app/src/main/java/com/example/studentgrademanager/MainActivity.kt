package com.example.studentgrademanager

// Core Android imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ViewModel imports
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentgrademanager.MainViewModel
// TODO: Import MainViewModel from the same package once created


/**
 * MainActivity is the entry point of the application.
 * It sets up the basic Compose UI.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the Compose UI
        setContent {
            MaterialTheme {
                Surface {
                    StudentGradeManager()
                    // TODO: Call the StudentGradeManager composable here
                }
            }
        }
    }
}

// TODO: Define the Student data class
data class Student(
    val name:String,
    val grade: Float
)

@Composable
fun StudentGradeManager(
    // TODO: Instantiate MainViewModel using viewModel()
    viewModel: MainViewModel = viewModel (),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {
        // Header
        // TODO: Add a Text composable for the header "Student Grade Manager"
       item {
           Text(
               text = "Student Grade Manager",
               style = MaterialTheme.typography.headlineMedium,
               modifier = Modifier.padding(bottom = 16.dp))
       }

        // TODO: Call GPADisplay composable, passing the GPA from the ViewModel
        item {
            GPADisplay(gpa = viewModel.calculateGPA())
        }


        // TODO: Call AddStudentForm composable, passing state and event handlers from the ViewModel
        item {
            AddStudentForm(name = viewModel.newStudentName,
                grade = viewModel.newStudentGrade,
                onNameChange = { viewModel.updateNewStudentName(it) },
                onGradeChange = { viewModel.updateNewStudentGrade(it) },
                onAddStudent =  { viewModel.addStudent(viewModel.newStudentName, viewModel.newStudentGrade) }
            )
        }

        // TODO: Create a Button to "Load Sample Data" that calls viewModel.loadSampleData()
        item {
            Button(
                onClick = { viewModel.loadSampleData() },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Load Sample Data")
            }
        }

        // TODO: Call StudentsList composable, passing the list of students and the remove student handler from the ViewModel
        item {
            StudentsList(viewModel.students, onRemoveStudent = { student -> viewModel.removeStudent(student) } )
        }

        // TODO: Conditionally show a CircularProgressIndicator if viewModel.isLoading is true
        item {
            if(viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun GPADisplay(gpa: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Add a Text composable for "Class GPA"
            Text(
                text = "Class GPA",
                style = MaterialTheme.typography.titleMedium
            )

            // TODO: Add a Text composable to display the formatted GPA (e.g., "%.2f".format(gpa))
            Text(
                text = "%.2f".format(gpa),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AddStudentForm(
    name: String,
    grade: String,
    onNameChange: (String) -> Unit,
    onGradeChange: (String) -> Unit,
    onAddStudent: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // TODO: Add a Text composable for "Add New Student"
            Text(
                text = "Add New Student",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // TODO: Create an OutlinedTextField for student name input
            OutlinedTextField(
                value = name,
                onValueChange = { onNameChange(it) },
                label = { Text("Student Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TODO: Create an OutlinedTextField for student grade input
            OutlinedTextField(
                value = grade,
                onValueChange = { onGradeChange(it) },
                label = { Text("Grade (0 - 100)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TODO: Create a Button to "Add Student"
            Button(
                onClick = {
                    if (name.isNotBlank() && grade.isNotBlank()) {
                        onAddStudent()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Student")
            }
        }
    }
}

@Composable
fun StudentsList(
    students: List<Student>, // TODO: Change Any to Student after defining Student data class
    onRemoveStudent: (Student) -> Unit // TODO: Change Any to Student after defining Student data class
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .heightIn(max = 300.dp) // Limit height to prevent overflow
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // TODO: Add a Text composable for "Students (${students.size})"
            Text(
                text = "Students (${students.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (students.isEmpty()) {
                // TODO: Add a Text composable for "No students added yet" if the list is empty
                Text(
                    text = "No students added yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else {
                // TODO: Create a LazyColumn to display the list of students
                LazyColumn (
                    modifier = Modifier.heightIn(max = 200.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(students){ index, student ->
                        StudentRow(
                            student = student,
                              onRemove = { onRemoveStudent(student) }
                        )
                        if (index < students.lastIndex){
                              HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentRow(
    student: Student, // TODO: Change Any to Student after defining Student data class
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // TODO: Add a Text composable to display student.name
            Text(
                text = student.name,
                style = MaterialTheme.typography.bodyLarge
            )

            // TODO: Add a Text composable to display "Grade: ${student.grade}"
            Text(
                text = "Grad: ${student.grade}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // TODO: Create an IconButton with Icons.Default.Delete for removing a student
        IconButton(
            onClick = { onRemove() }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remove student"
            )
        }
    }
}

/**
 * Preview function for the StudentGradeManager screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentGradeManagerPreview() {
    MaterialTheme {
        StudentGradeManager()
        // TODO: Call StudentGradeManager here for preview
    }
}
