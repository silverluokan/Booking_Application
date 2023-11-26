package com.example.booking_application.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_application.Models.Book
import com.example.booking_application.Prompt.edt_book
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.BooklistAdptBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class BookListAdapter(var Books: List<Book>, private var context: Context): RecyclerView.Adapter<BookListAdapter.BooksViewHolder>() {


    private lateinit var coroutine: CoroutineContext


    inner class BooksViewHolder(val binding: BooklistAdptBinding) : RecyclerView.ViewHolder(binding.root), edt_book.onEdtDismissInterface {
        fun bindBookList(ReadList: Book){
            binding.bookauthor.text = ReadList.Author
            binding.booktitle.text = ReadList.BookName.uppercase()
            binding.bookvolumepages.text = "Pages: ${ReadList.Pages}, Volume: ${ReadList.Volume}"
            binding.favBtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.updateBookStatus(ReadList.id, "Favorite")
                        val DataB = DataBase.queryBook("List")
                        updateBookList(DataB)
                    }
                }
            }
            binding.edtBtn.setOnClickListener{
                val args = Bundle()
                args.putString("book_id", ReadList.id.toString())
                args.putString("book_name", ReadList.BookName)
                args.putString("book_author", ReadList.Author)
                args.putString("book_pages", ReadList.Pages)
                args.putString("book_volume", ReadList.Volume)
                var dialog = edt_book()
                dialog.arguments = args
                dialog.onDismissInterfaceCallBack = this
                dialog.show((context as AppCompatActivity).supportFragmentManager,"edt_books")
            }
            binding.remBtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.updateBookStatus(ReadList.id, "Archive")
                        val DataB = DataBase.queryBook("List")
                        updateBookList(DataB)
                    }
                }
            }
        }

        override fun getData() {
            val DataB = DataBase.queryBook("List")
            updateBookList(DataB)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookListAdapter.BooksViewHolder {
        return BooksViewHolder(BooklistAdptBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookListAdapter.BooksViewHolder, position: Int) {
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