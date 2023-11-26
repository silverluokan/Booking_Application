package com.example.booking_application.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booking_application.Adapter.BookListAdapter
import com.example.booking_application.Prompt.add_book
import com.example.booking_application.R
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.FragmentBookListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class book_list : Fragment(), View.OnClickListener, add_book.onAddDismissInterface {
    private lateinit var binding: FragmentBookListBinding
    private lateinit var adapter: BookListAdapter
    private lateinit var llm:LinearLayoutManager
    private lateinit var coroutine: CoroutineContext

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookListBinding.inflate(inflater,container,false)
        binding.addbookPnlBtn.setOnClickListener(this)

        val DataB = DataBase.queryBook("List")
        adapter = BookListAdapter(DataB,requireContext())
        if(DataB.size > 0){
            binding.recyclerView.adapter = adapter
            llm = LinearLayoutManager(this.context)
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = llm
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.addbook_pnl_btn->{
                var dialog = add_book()
                dialog.onDismissInterfaceCallBack = this
                dialog.show(getParentFragmentManager(),"AddBook")
            }
        }
    }

    override fun getData() {
        val DataB = DataBase.queryBook("List")
        adapter.updateBookList(DataB)
    }
}