package com.example.studentgrademanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    var students by mutableStateOf<List<Student>>(emptyList())
        private set // 'private set' means the 'students' list can be read externally but only modified within this ViewModel.

    var isLoading by mutableStateOf(false)
        private set

    var newStudentName by mutableStateOf("")
        private set

    var newStudentGrade by mutableStateOf("")
        private set

    fun addStudent(name: String, grade: String) {
        // TODO: Implement the addStudent function
        val convertGrade = grade.toFloatOrNull()
        if(name.isNotBlank() && convertGrade != null){
            val newStudent = Student(name = name.trim(), grade = convertGrade)
            students = students + newStudent
            newStudentName = ""
            newStudentGrade = ""
        }
    }

    fun removeStudent(student: Student) {
        // TODO: Implement the removeStudent function
        students = students.filterNot { it.name == student.name
                && it.grade == student.grade }
    }

    fun calculateGPA(): Float {
        // TODO: Implement the calculateGPA function
        return if (students.isEmpty()) {
            0f
        } else {
            val avg = students.map { it.grade }.average()
            String.format("%.2f",avg).toFloat()
        }
    }

    fun loadSampleData() {
        // TODO: Implement the loadSampleData function using coroutines (viewModelScope.launch)
        viewModelScope.launch {
            isLoading = true
            delay(1500L)
            students = listOf(
                Student("Beau Hotz", 95f),
                Student("Jacob Baumgardner", 75f),
                Student("Jaimie Barr", 98f),
                Student("Jessica Parker", 85f)
            )
            isLoading = false
        }
    }

    fun updateNewStudentName(name: String) {
        // TODO: Implement the updateNewStudentName function
        newStudentName = name
    }

    fun updateNewStudentGrade(grade: String) {
        // TODO: Implement the updateNewStudentGrade function
       newStudentGrade = grade
    }
}


