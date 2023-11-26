package com.example.booking_application.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_application.Fragments.book_list
import com.example.booking_application.Models.Book
import com.example.booking_application.Prompt.edt_book
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.ArchivelistAdptBinding
import com.example.booking_application.databinding.FavelistAdptBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BookArchiveAdapter(var Books: List<Book>, private var context: Context): RecyclerView.Adapter<BookArchiveAdapter.BooksViewHolder>() {


    private lateinit var coroutine: CoroutineContext

    inner class BooksViewHolder(val binding: ArchivelistAdptBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindBookList(ReadList: Book){
            binding.bookNameArchive.text = ReadList.BookName.uppercase()
            binding.undoBtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.updateBookStatus(ReadList.id, "List")
                        val DataB = DataBase.queryBook("Archive")
                        updateBookList(DataB)
                    }
                }
            }
            binding.deleteBtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.deleteBook(ReadList.id)
                        val DataB = DataBase.queryBook("Archive")
                        updateBookList(DataB)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookArchiveAdapter.BooksViewHolder {
        return BooksViewHolder(ArchivelistAdptBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookArchiveAdapter.BooksViewHolder, position: Int) {
        val Readme = Books[position]
        holder.bindBookList(Readme)
    }

    override fun getItemCount(): Int {
        return Books.size
    }

    fun updateBookList(bookList: ArrayList<Book>){
        this.Books = arrayListOf()
        notifyDataSetChanged()
        this.Books = bookList
        this.notifyItemInserted(this.Books.size)
    }
}