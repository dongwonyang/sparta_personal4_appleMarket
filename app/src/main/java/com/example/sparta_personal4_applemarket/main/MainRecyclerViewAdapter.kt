package com.example.sparta_personal4_applemarket.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sparta_personal4_applemarket.ListFormat
import com.example.sparta_personal4_applemarket.SaleDetailActivity
import com.example.sparta_personal4_applemarket.databinding.ItemSalelistBinding
import java.text.DecimalFormat

class MainRecyclerViewAdapter(private val itemList: List<ListFormat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    open lateinit var binding: ItemSalelistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSalelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (holder is ViewHolder) holder.bind(item)
    }

    class ViewHolder(private val binding: ItemSalelistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListFormat) {
            with(binding) {
                val imageId = itemView.resources.getIdentifier(
                    item.fileName,
                    "drawable",
                    itemView.context.packageName
                )
                ivSaleImage.setImageResource(imageId)
                tvProductName.text = item.productName
                tvAddress.text = item.address
                tvPrice.text = DecimalFormat("#,###").format(item.price)
                tvChatNum.text = item.chatNum.toString()
                tvLikesNum.text = item.likesNum.toString()
            }

            binding.clParent.setOnClickListener{
                val intent = Intent(binding.root.context, SaleDetailActivity::class.java)
                intent.putExtra("data", item)
                binding.root.context.startActivity(intent)
            }
        }
    }

}