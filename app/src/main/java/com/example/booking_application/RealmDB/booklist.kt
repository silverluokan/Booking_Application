package com.example.booking_application.RealmDB

import com.example.booking_application.Fragments.book_list
import com.example.booking_application.Models.Book
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class booklist : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var Author: String = ""
    var BookName: String = ""
    var Volume: String = ""
    var Pages: String = ""
    var DateBookAdded: String = ""
    var DateModified: String = ""
    var BookStatus: String = ""
}

object DataBase {
    val configuration = RealmConfiguration.create(setOf(booklist::class))
    val s = configuration.deleteRealmIfMigrationNeeded
    val realm = Realm.open(configuration)

    suspend fun write(BAuthor:String, BName:String, BVolume:String, Bpages:String, BAddedDate:String, BModifiedDate:String, BStatus:String) {

        val BlName = booklist().apply {
            Author = BAuthor
            BookName = BName
            Volume = BVolume
            Pages = Bpages
            DateBookAdded = BAddedDate
            DateModified = BModifiedDate
            BookStatus = BStatus
        }

        realm.write {
            copyToRealm(BlName)
        }

    }

    fun queryBook(BStatus:String): ArrayList<Book> {
        val all: RealmResults<booklist> = realm.query<booklist>().find()
        val bList = mutableListOf<Book>()
        all.forEach { Unit ->
            if(Unit.BookStatus == BStatus)
                bList.add(Book(
                    Unit.id,
                    Unit.Author,
                    Unit.BookName,
                    Unit.Volume,
                    Unit.Pages,
                    Unit.DateBookAdded,
                    Unit.DateModified,
                    Unit.BookStatus))
        }
        return bList as ArrayList<Book>
    }

    suspend fun updateBookStatus(id: ObjectId, newStatus:String){
        realm.write {
            val book = query<booklist>("id == $0", id).find().first()
            book.BookStatus = newStatus
        }
    }

    suspend fun updateBookData(id: ObjectId, bookName:String,bookAuthor:String,bookVolume:String,bookPage:String){
        realm.write {
            val book = query<booklist>("id == $0", id).find().first()
            book.BookName = bookName
            book.Author = bookAuthor
            book.Volume= bookVolume
            book.Pages = bookPage
        }
    }

    suspend fun deleteBook(id: ObjectId){
        realm.write {
            val books: booklist = query<booklist>("id == $0", id).find().first()
            delete(books)
        }
    }
    suspend fun deleteAllBook(BookStats: String){
        realm.write {
            val books = query<booklist>("BookStatus == $0", BookStats).find()
            delete(books)
        }
    }
}