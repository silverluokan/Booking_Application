package com.example.booking_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.booking_application.R
import com.example.booking_application.databinding.FragmentArchiveBookBinding

class archive_book : Fragment() {
    private lateinit var binding: FragmentArchiveBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchiveBookBinding.inflate(inflater,container,false)
        return binding.root
    }

}