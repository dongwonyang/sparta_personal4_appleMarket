package com.example.sparta_personal4_applemarket.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sparta_personal4_applemarket.ListData
import com.example.sparta_personal4_applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSaleList.adapter = MainRecyclerViewAdapter(ListData.data)
        binding.rvSaleList.layoutManager = LinearLayoutManager(this)
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        setAlertBack()
    }

    fun setAlertBack() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("종료하시겠습니까?")

        val listener = DialogInterface.OnClickListener{ dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> finish()
            }
        }

        builder.setPositiveButton("예", listener)
        builder.setNegativeButton("아니오", listener)

        builder.show()
    }
}