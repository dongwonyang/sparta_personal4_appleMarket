package com.example.sparta_personal4_applemarket.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.sparta_personal4_applemarket.data.ListData
import com.example.sparta_personal4_applemarket.data.ListFormat
import com.example.sparta_personal4_applemarket.R
import com.example.sparta_personal4_applemarket.ui.SaleDetailActivity
import com.example.sparta_personal4_applemarket.data.UserData
import com.example.sparta_personal4_applemarket.databinding.ItemSalelistBinding
import java.text.DecimalFormat

class MainRecyclerViewAdapter(private val itemList: List<ListFormat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    open lateinit var binding: ItemSalelistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSalelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) { position ->
            ListData.data.removeAt(position)
            UserData.likesList.remove(position)
            UserData.likesList = UserData.likesList.map { if (it > position) it - 1 else it }.toMutableList()
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (holder is ViewHolder) holder.bind(item, position)
    }

    class ViewHolder(
        private val binding: ItemSalelistBinding,
        private val onDeleteItem: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListFormat, position: Int) {
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

                if (UserData.likesList.contains(position)) {
                    ivIcHeart.setImageResource(R.drawable.ic_heart_selected)
                } else ivIcHeart.setImageResource(R.drawable.ic_heart)

                clParent.setOnClickListener {
                    val intent = Intent(root.context, SaleDetailActivity::class.java)
                    intent.putExtra("data", item)
                    root.context.startActivity(intent)
                }

                clParent.setOnLongClickListener {
                    val builder = AlertDialog.Builder(root.context)
                    builder.setMessage("삭제하시겠습니까?")

                    val listener = DialogInterface.OnClickListener { dialog, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                onDeleteItem(position)
                            }

                            DialogInterface.BUTTON_NEGATIVE -> {}
                        }
                    }
                    builder.setPositiveButton("예", listener)
                    builder.setNegativeButton("아니요", listener)
                    builder.show()
                    true
                }

            }
        }
    }

}


