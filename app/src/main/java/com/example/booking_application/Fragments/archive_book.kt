package com.example.booking_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booking_application.Adapter.BookArchiveAdapter
import com.example.booking_application.Adapter.BookListAdapter
import com.example.booking_application.R
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.ArchivelistAdptBinding
import com.example.booking_application.databinding.FragmentArchiveBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class archive_book : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentArchiveBookBinding
    private lateinit var adapter: BookArchiveAdapter
    private lateinit var llm:LinearLayoutManager
    private lateinit var coroutine: CoroutineContext
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchiveBookBinding.inflate(inflater,container,false)

        val DataB = DataBase.queryBook("Archive")
        adapter = BookArchiveAdapter(DataB,requireContext())
        if(DataB.size > 0){
            binding.ArchiveList.adapter = adapter
            llm = LinearLayoutManager(this.context)
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.ArchiveList.layoutManager = llm
        }
        binding.delallBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.delall_btn-> {
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.deleteAllBook("Archive")
                        val DataB = DataBase.queryBook("Archive")
                        adapter.updateBookList(DataB)
                    }
                }
            }
        }
    }
}