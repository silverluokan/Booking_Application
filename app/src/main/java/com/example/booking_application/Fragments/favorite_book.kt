package com.example.booking_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booking_application.Adapter.BookFavoriteAdapter
import com.example.booking_application.Adapter.BookListAdapter
import com.example.booking_application.R
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.FragmentBookListBinding
import com.example.booking_application.databinding.FragmentFavoriteBookBinding

class favorite_book : Fragment() {
    private lateinit var binding: FragmentFavoriteBookBinding
    private lateinit var adapter: BookFavoriteAdapter
    private lateinit var llm: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBookBinding.inflate(inflater,container,false)
        val DataB = DataBase.queryBook("Favorite")
        adapter = BookFavoriteAdapter(DataB,requireContext())
        if(DataB.size > 0){
            binding.FavoriteList.adapter = adapter
            llm = LinearLayoutManager(this.context)
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.FavoriteList.layoutManager = llm
        }
        return binding.root
    }

}