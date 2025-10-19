package com.example.studentlogin

// Core Android imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// Navigation imports
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * This project covers concepts from Chapter 9 lessons:
 * - "Introduction to Navigation" - for setting up basic navigation structure
 * - "Passing Arguments" - for passing user data between screens
 * - "Managing the Back Stack" - for controlling navigation flow
 * - "Navigation UI Components" - for creating user interface elements
 * - "Organizing Navigation with Multiple Files" - for project structure
 *
 * Students should review these lessons before starting:
 * - Introduction to Navigation lesson for basic navigation setup
 * - Passing Arguments lesson for data transfer between screens
 * - Managing the Back Stack lesson for navigation control
 * - Navigation UI Components lesson for UI design
 * - Organizing Navigation with Multiple Files lesson for project organization
 */

/**
 * MainActivity is the entry point of the application.
 * It sets up the basic window configuration and initializes the Compose UI with navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure the window to use light status bar icons
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Set up the Compose UI with navigation
        setContent {
            MaterialTheme {
                Surface {
                    // TODO: Call the main navigation app function here
                    LoginApp()
                }
            }
        }
    }
}

/**
 * Main navigation app that handles the login flow
 */
@Composable
fun LoginApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {

            LoginScreen(onLoginSuccess = { userName ->
                navController.navigate("welcome/$userName")
            })
        }
        composable(
            route = "welcome/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            WelcomeScreen(
                userName = userName,
                onViewProfile = { navController.navigate("profile/$userName") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "profile/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            ProfileScreen(
                userName = userName,
                onBackToWelcome = {
                    navController.navigate("welcome/$userName")
                }
            )
        }
    }
}

/**
 * Login screen with validation using Chapter 7 concepts
 */
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    //Input Values
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginAttempted by remember { mutableStateOf(false) }

    //Interaction tracking
    var nameInteraction by remember { mutableStateOf(false) }
    var emailInteraction by remember { mutableStateOf(false) }
    var passwordInteraction by remember { mutableStateOf(false) }

    //HardCode email and pwd + error variables
    val validEmail = "student@wccnet.edu"
    val validPassword = "password123"

    val nameError = nameInteraction && name.isBlank()
    val emailBlank = email.isBlank()
    val emailBadForm = !isValidEmail(email)
    val emailIncorrect = loginAttempted && emailInteraction && email != validEmail
    val emailError = emailInteraction && (emailBlank|| emailBadForm || emailIncorrect)
    val passwordError = passwordInteraction && (password.isBlank() || (loginAttempted && password != validPassword))

    //Pwd visbility state
    var passwordVisible by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Student Login",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameInteraction = true
                    },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    isError = nameError,
                    supportingText = {
                        if (nameError) {
                            Text(
                                text = "Please enter your name",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailInteraction = true
                    },
                    label = { Text(text = "Email Address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    isError = emailInteraction && emailError,
                    supportingText = {
                        if (emailInteraction) {
                            when {
                                emailBadForm -> Text(
                                    text = "Please enter a valid email",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                emailError -> Text(
                                    text = "Please enter the correct email",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordInteraction = true
                    },
                    label = { Text(text = "Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        val visibilityIcon =
                            if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = visibilityIcon,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) {
                            Text(
                                text = "Please enter the correct password",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        nameInteraction = true
                        emailInteraction = true
                        passwordInteraction = true
                        loginAttempted = true

                        val nameValid = name.isNotBlank()
                        val emailValid = email.isNotBlank() && email == validEmail
                        val passwordValid = password == validPassword

                        if (nameValid && emailValid && passwordValid) {
                            onLoginSuccess(name.trim())
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Login")
                }
            }
            Text(
                text = "Demo $validEmail / $validPassword",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


/**
 * Welcome screen that displays after successful login
 */
@Composable
fun WelcomeScreen(
    userName: String,
    onViewProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Hello ${userName}!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(32.dp)
        )
        Button(
            onClick = onViewProfile,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)

        ) {
            Text(text = "View Profile")
        }
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ){
            Text(text = "Logout")
        }
    }
}

/**
 * Profile screen showing user information
 */
@Composable
fun ProfileScreen(
    userName: String,
    onBackToWelcome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileRow("Name ", value = userName)
                ProfileRow("Email ", value =  " Zbigham@wccnet.edu")
                ProfileRow("Student ID ", value =  " 12345678")
                ProfileRow("Major ", value = " Computer Science")
                ProfileRow("Year ", value = " 2026")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBackToWelcome){
            Text("Back to Welcome")
        }
    }
}

/**
 * Helper composable for profile information rows
 */
@Composable
fun ProfileRow(label: String, value: String) {
    Row (
        //modifier = Modifier
        //.fillMaxWidth()
        //.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        Text (
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    // Reference: Navigation UI Components lesson
}

/**
 * Email validation function using regex (Chapter 7: Regular Expressions)
 */
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return emailRegex.matches(email)
}

/**
 * Preview function for the login screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {})
    }
}

/**
 * Preview function for the welcome screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            userName = "John Doe",
            onViewProfile = {},
            onLogout = {}
        )
    }
}

/**
 * Preview function for the profile screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            userName = "John Doe",
            onBackToWelcome = {}
        )
    }
}
