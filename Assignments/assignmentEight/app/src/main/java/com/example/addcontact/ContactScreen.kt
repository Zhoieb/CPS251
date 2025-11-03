package com.example.addcontact

import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(viewModel: ContactViewModel) {
    val contacts by viewModel.allContacts.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()


    var name by remember { mutableStateOf("") }
    var nameInteraction by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var phoneNumberInteraction by remember { mutableStateOf(false) }
    val phoneBadForm = !viewModel.isValidPhoneNumber(phoneNumber)
    val phoneNumError = phoneNumberInteraction && phoneBadForm && phoneNumber.isBlank()
    val isFormValid = name.trim().isNotBlank() && viewModel.isValidPhoneNumber(phoneNumber)

    var showDeleteDialog by remember { mutableStateOf(false) }
    var contactToDelete by remember { mutableStateOf<Contact?>(null) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),

        ) { //Form + Buttons + Search
        item {
            //Name Input
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("Name") },
                isError = nameInteraction && name.isBlank(),
                supportingText = {
                    if (nameInteraction && name.isBlank()) {
                        Text(
                            text = "Name cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            //Phone Input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number (10 digits)") },
                isError = phoneNumError,
                supportingText = {
                    if (phoneNumError) {
                        Text(
                            text = "Invalid phone number format should be like 999.999.9999",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall

                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Row for Add / Sort buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                val buttonModifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                Button(
                    onClick = {
                        nameInteraction = true
                        phoneNumberInteraction = true
                        if (isFormValid) {
                            viewModel.insert(
                                Contact(
                                    name = name.trim(),
                                    phoneNumber = phoneNumber.trim()
                                )
                            )
                            name = ""
                            phoneNumber = ""
                            nameInteraction = false
                            phoneNumberInteraction = false
                        }
                    },
                    modifier = buttonModifier
                ) {
                    Text(text = "Add")
                }
                Button(
                    onClick = { viewModel.onSortOrderChange(SortOrder.DESC) },
                    modifier = buttonModifier
                ) {
                    Text(text = "Sort Desc")
                }
                Button(
                    onClick = { viewModel.onSortOrderChange(SortOrder.ASC) },
                    modifier = buttonModifier
                ) { Text(text = "Sort Asc") }
            }
        }
        item {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Search Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            //Divider
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Contacts:",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } //Contact title

        items(contacts) { contact ->
            ContactItem(
                contact = contact,
                onDelete = {
                    contactToDelete = it
                    showDeleteDialog = true
                }
            )
        }
    }



    if (showDeleteDialog && contactToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                contactToDelete = null
            },
            title = { Text("Delete Contact") },
            text = { Text("Are you sure you want to delete this contact?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.delete(contactToDelete!!)
                    showDeleteDialog = false
                    contactToDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    contactToDelete = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

}

@Composable
fun ContactItem(
    contact: Contact,
    onDelete: (Contact) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text ( contact.name, style = MaterialTheme.typography.bodyLarge)
                Text( contact.phoneNumber, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onDelete(contact) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Contact")
            }
        }
        HorizontalDivider()
    }
}






