package com.example.addcontact

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String,
    val phoneNumber: String
)





