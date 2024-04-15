package com.example.sparta_personal4_applemarket.ui

import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sparta_personal4_applemarket.R
import com.example.sparta_personal4_applemarket.data.ListData
import com.example.sparta_personal4_applemarket.data.ListFormat
import com.example.sparta_personal4_applemarket.data.UserData
import com.example.sparta_personal4_applemarket.databinding.ActivitySaleDetailBinding
import java.text.DecimalFormat

class SaleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaleDetailBinding
    private val data by lazy { intent.getParcelableExtra<ListFormat>("data") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_detail)
        binding = ActivitySaleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMT.paintFlags = binding.tvMT.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        data?.let { getUi(it) }
        binding.ivBack.setOnClickListener {
            finish()
        }

        clickHeart()
    }

    fun getUi(data: ListFormat) {
        with(binding) {
            val imageResource = resources.getIdentifier(data.fileName, "drawable", packageName)
            ivMain.setImageResource(imageResource)

            tvSeller.text = data.seller
            tvAddress.text = data.address
            tvProductName.text = data.productName
            tvProductInfo.text = data.productIntro
            tvPrice.text = DecimalFormat("#,###").format(data.price)
        }
    }

    fun clickHeart() {
        val selected = R.drawable.ic_heart_selected
        val noneSelected = R.drawable.ic_heart
        val position = ListData.data.indexOf(data)

        if (UserData.likesList.contains(position)) {
            binding.ivHeart.setImageResource(selected)
        }

        binding.ivHeart.setOnClickListener {
            if (UserData.likesList.contains(position)) {
                binding.ivHeart.setImageResource(noneSelected)
                UserData.likesList.remove(position)
                ListData.data[position] = ListData.data[position].copy(
                    likesNum = ListData.data[position].likesNum - 1
                )
            } else {
                binding.ivHeart.setImageResource(selected)
                UserData.likesList.add(position)
                ListData.data[position] = ListData.data[position].copy(
                    likesNum = ListData.data[position].likesNum + 1
                )
            }
        }
    }
}