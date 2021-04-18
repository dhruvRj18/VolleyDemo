package com.example.volleydemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcvAdapter(
    val list: ArrayList<UserModel>,
    val listener:MyOnClickListener
) : RecyclerView.Adapter<RcvAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTv = itemView.findViewById<TextView>(R.id.itemName)
        val idTv = itemView.findViewById<TextView>(R.id.itemId)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.OnClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            idTv.text = item.id
            nameTv.text = item.name
        }
    }

    override fun getItemCount(): Int {
         return list.size
    }

    interface MyOnClickListener{
        fun OnClick(position: Int)
    }
}