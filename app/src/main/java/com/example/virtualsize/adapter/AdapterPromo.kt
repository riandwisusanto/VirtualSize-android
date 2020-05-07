package com.example.virtualsize.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.virtualsize.R

class AdapterPromo(private val image : IntArray, private val mContext : Context) :
    RecyclerView.Adapter<ListPromo>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPromo {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_promo, parent, false)
        return ListPromo(v, mContext)
    }

    override fun onBindViewHolder(holder: ListPromo, position: Int) {
        holder.index(image[position])
    }

    override fun getItemCount(): Int {
        return image.size
    }
}

class ListPromo(itemView: View, private val mContext: Context) : RecyclerView.ViewHolder(itemView) {
    private val imgPromo : ImageView
    init {
        imgPromo = itemView.findViewById<View>(R.id.imgPromo) as ImageView
    }
    fun index(item : Int) {
        Glide.with(mContext).load(item).into(imgPromo)
    }
}