package com.example.booking_application.RealmDB

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class bookarchive: RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()
    val Author: String = ""
    val BookName: String = ""
    val DateBookPublished: String = ""
    val DateAdded: String = ""
    val DateModified: String = ""

}