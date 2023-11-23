package com.example.booking_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booking_application.R
import com.example.booking_application.databinding.FragmentBookListBinding
import com.example.booking_application.databinding.FragmentFavoriteBookBinding

class favorite_book : Fragment() {
    private lateinit var binding: FragmentFavoriteBookBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBookBinding.inflate(inflater,container,false)
        return binding.root
    }
}