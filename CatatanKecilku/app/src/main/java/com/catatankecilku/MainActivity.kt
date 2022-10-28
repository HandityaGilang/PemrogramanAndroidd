package com.catatankecilku

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatankecilku.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var btn: FloatingActionButton

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel:MyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this,MyListModelFactory(androidx.preference.PreferenceManager.getDefaultSharedPreferences(this))
        ).get(MyListViewModel::class.java)

        val adapter =ListSelectionRecycleViewAdapter(viewModel.lists)

        viewModel.onListAdded={
            adapter.listsUpdate()
        }
        binding.listsRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.listsRecyclerView.adapter=adapter
        binding.add.setOnClickListener({
            viewModel.saveList(Daftar("dendy"))
        })
        btn=findViewById(R.id.add)
        btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val builder1 = AlertDialog.Builder(this@MainActivity)
                builder1.setTitle("Masukan Task Yang Ingin Ditambahkan")
                val input = EditText(this@MainActivity)
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder1.setView(input)
                builder1.setPositiveButton("Tambah", DialogInterface.OnClickListener { dialog, which -> viewModel.saveList(Daftar(input.text.toString()))})
                val alert11 = builder1.create()
                alert11.show()
            }

        })
    }

}