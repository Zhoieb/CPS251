package com.example.addcontact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.addcontact.ui.theme.AddContactTheme

class MainActivity : ComponentActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ContactViewModel.provideFactory(application)
        contactViewModel = ViewModelProvider(this, factory) [ContactViewModel::class.java]

        setContent {
            AddContactTheme {
                ContactScreen(viewModel = contactViewModel)
            }
        }
    }
}
