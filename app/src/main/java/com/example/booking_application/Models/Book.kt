package com.example.booking_application.Models

data class Book(
    val id: String,
    val Author: String,
    val BookName: String,
    val DateBookPublished: String = "",
    val DateAdded: String = "",
    val DateModified: String = ""
)