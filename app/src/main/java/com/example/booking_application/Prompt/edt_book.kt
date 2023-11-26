package com.example.booking_application.Prompt

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.booking_application.Adapter.BookListAdapter
import com.example.booking_application.Fragments.book_list
import com.example.booking_application.R
import com.example.booking_application.RealmDB.DataBase
import com.example.booking_application.databinding.FragmentEdtBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import kotlin.coroutines.CoroutineContext

class edt_book : DialogFragment(), View.OnClickListener{

    private lateinit var binding: FragmentEdtBookBinding
    private lateinit var coroutine: CoroutineContext
    lateinit var onDismissInterfaceCallBack: onEdtDismissInterface
    var ObjectIdHex = ""

    interface onEdtDismissInterface {
        fun getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEdtBookBinding.inflate(inflater,container,false)
        val b = arguments
        var frombundle = b!!.getString("book_id","")
        var bundlesplit = frombundle.split('(',')')
        ObjectIdHex = bundlesplit[1]
        binding.bookNameTvEdt.setText(b.getString("book_name",""))
        binding.bookAuthorTvEdt.setText(b.getString("book_author",""))
        binding.bookVolumeTvEdt.setText(b.getString("book_volume",""))
        binding.bookPagesTvEdt.setText(b.getString("book_pages",""))
        binding.cnledtBtn.setOnClickListener(this)
        binding.edtBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.edt_btn-> {
                if(binding.bookNameTvEdt.text.isNullOrEmpty() || binding.bookAuthorTvEdt.text.isNullOrEmpty() || binding.bookPagesTvEdt.text.isNullOrEmpty()){
                    Toast.makeText(context,"Fill Up All The Textbox", Toast.LENGTH_SHORT)
                }else{
                    coroutine = Job() + Dispatchers.IO
                    val scope = CoroutineScope(coroutine)
                    scope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            DataBase.updateBookData(
                                ObjectId(ObjectIdHex),
                                binding.bookNameTvEdt.text.toString(),
                                binding.bookAuthorTvEdt.text.toString(),
                                binding.bookVolumeTvEdt.text.toString(),
                                binding.bookPagesTvEdt.text.toString()
                            )
                            onDismissInterfaceCallBack.getData()
                        }
                    }
                    Toast.makeText(context,"Succesfully Updated!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
            R.id.cnledt_btn->{
                dismiss()
            }
        }
    }
}