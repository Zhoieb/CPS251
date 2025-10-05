package com.example.contactinformation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



/**
 * This project covers concepts from Chapter 7 lessons:
 * - "Validation" - for form validation and error handling
 * - "Managing Input State" - for state management in forms
 * - "Text Fields" - for input field styling and error states
 * - "Regular Expressions" - for email, phone, and ZIP code validation
 *
 * Students should review these lessons before starting:
 * - Validation lesson for form validation patterns
 * - Managing Input State lesson for state management
 * - Text Fields lesson for input field styling
 * - Regular Expressions lesson for validation patterns
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactValidatorApp()
            }
        }
    }
}

@Composable
fun ContactValidatorApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ContactForm()
    }
}

@Composable
fun ContactForm() {
    // TODO: Create state variables for form fields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    // TODO: Create validation state variables
    val isNameValid = name.isNotBlank()
    val isEmailValid = validateEmail(email)
    val isPhoneValid = validatePhone(phone)
    val isZipValid = validateZipCode(zipCode)

    // TODO: Create submitted information state variable
    var submittedInfo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp
            ),
        // TODO: Arrange items vertically with a reasonable dp spacing
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TODO: Call the NameField composable here
        NameField(name, isNameValid = isNameValid, onValueChange = {name = it})

        // TODO: Call the EmailField composable here
        EmailField(email, isEmailValid, onValueChange = {email = it})

        // TODO: Call the PhoneField composable here
        PhoneField(phone, isPhoneValid, onValueChange = {phone = it})

        // TODO: Call the ZipCodeField composable here
        ZipCodeField(zipCode, isZipValid, onValueChange = {zipCode = it})

        // TODO: Create the Submit button
        Button(
            onClick = {submittedInfo = "Name: $name\nEmail: $email\nPhone: $phone\nZIP: $zipCode"},
            modifier = Modifier.fillMaxWidth(),
            enabled = isNameValid && isEmailValid && isPhoneValid && isZipValid && name.isNotEmpty() && email.isNotEmpty() &&
                    phone.isNotEmpty() && zipCode.isNotEmpty(),
        ) {
            Text(text = "Submit")
        }

        if(submittedInfo.isNotEmpty()) {
            Text(
                text = submittedInfo,
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun NameField(
    name: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the name input field
    OutlinedTextField(
        value = name,
        onValueChange = onValueChange,
        label = { Text("Full Name") },
        placeholder = { Text ("Full Name") },
        modifier = Modifier.fillMaxWidth(),
        isError = !isNameValid,

        supportingText = {
            if (!isNameValid && name.isNotEmpty()) {
                Text(
                    text = "Please enter a valid Full Name",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

@Composable
fun EmailField(
    email: String,
    isEmailValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the email input field
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("Email Address") },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text (text = "Email Address") },
        isError = !isEmailValid && email.isNotEmpty(),

        supportingText = {
            if (!isEmailValid && email.isNotEmpty()) {
                Text(
                    text = "Please enter a valid Email Address",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

@Composable
fun PhoneField(
    phone: String,
    isPhoneValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the phone input field
    OutlinedTextField(
        value = phone,
        onValueChange = onValueChange,
        label = { Text (text = "Phone Number") },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text ("Phone Number") },
        isError =  !isPhoneValid && phone.isNotEmpty(),

        supportingText = {
            if (!isPhoneValid && phone.isNotEmpty()) {
                Text(
                    text = "Please enter a valid Phone Number",
                    color = MaterialTheme.colorScheme.error,
                    style
                    = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

@Composable
fun ZipCodeField(
    zipCode: String,
    isZipValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the ZIP code input field
    OutlinedTextField(
        value = zipCode,
        onValueChange = onValueChange,
        label = { Text ("Zip Code") },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text ("Zip Code") },
        isError = !isZipValid && zipCode.isNotEmpty(),

        supportingText = {
            if (!isZipValid && zipCode.isNotEmpty()) {
                Text(
                    text = "Please enter a valid Zip Code",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

// TODO: Create validation functions using regex
fun validateEmail (email:String): Boolean {
    val emailRegex = "^[A-Za-z-0-9+_.-]+@(.+)$".toRegex()
    return emailRegex.matches(email)
}

fun validatePhone(phone:String): Boolean {
    val phoneRegex = "^\\d{3}[-/\\s]\\d{3}[-/\\s]\\d{4}$".toRegex()
    return phoneRegex.matches(phone)
}

fun validateZipCode(zipCode: String):Boolean {
    val zipRegex = "^\\d{5}$".toRegex()
    return zipRegex.matches(zipCode)
}
//when button is clicked and all fields are valid and not empty, the submitted information should be displayed
//in a text field below the button.

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactValidatorAppPreview() {
    ContactValidatorApp()
}