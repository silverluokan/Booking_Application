package com.example.booking_application.Models

import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

data class Book(
    val id: ObjectId,
    val Author: String,
    val BookName: String,
    val Volume: String,
    val Pages: String,
    val DateBookAdded: String,
    val DateModified: String,
    val BookStatus: String
)