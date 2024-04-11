package com.example.sparta_personal4_applemarket

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sparta_personal4_applemarket.databinding.ActivitySaleDetailBinding
import java.text.DecimalFormat

class SaleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_detail)
        binding = ActivitySaleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMT.paintFlags = binding.tvMT.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val data = intent.getParcelableExtra<ListFormat>("data")

        data?.let { getUi(it) }
    }

    fun getUi(data: ListFormat){
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
}