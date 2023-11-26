package com.example.booking_application.Prompt

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.booking_application.R
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.FragmentAddBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext


class add_book : DialogFragment(), View.OnClickListener{
    private lateinit var binding: FragmentAddBookBinding
    private lateinit var coroutine: CoroutineContext
    lateinit var onDismissInterfaceCallBack: onAddDismissInterface


    interface onAddDismissInterface {
        fun getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBookBinding.inflate(inflater,container,false)
        binding.addBtn.setOnClickListener(this)
        binding.cnlBtn.setOnClickListener(this)
        coroutine = Job() + Dispatchers.IO
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTime = LocalDateTime.now().format(formatter)
        when(v!!.id){
            R.id.add_btn->{
                if(binding.bookAuthorTv.text.isNullOrEmpty() || binding.bookNameTv.text.isNullOrEmpty() || binding.bookPagesTv.text.isNullOrEmpty()){
                    Toast.makeText(context,"Fill Up All The Textbox", Toast.LENGTH_SHORT)
                }
                else {
                    val scope = CoroutineScope(coroutine)
                    scope.launch(Dispatchers.IO) {

                        withContext(Dispatchers.Main) {
                            DataBase.write(
                                binding.bookAuthorTv.text.toString(),
                                binding.bookNameTv.text.toString(),
                                binding.bookVolumeTv.text.toString(),
                                binding.bookPagesTv.text.toString(),
                                dateTime,
                                dateTime,
                                "List"
                            )
                            onDismissInterfaceCallBack.getData()
                        }
                    }
                    Toast.makeText(context,"Succesfully Inserted!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
            R.id.cnl_btn->{
                dismiss()
            }
        }
    }

}