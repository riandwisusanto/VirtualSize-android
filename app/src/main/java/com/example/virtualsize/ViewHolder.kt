package com.example.virtualsize

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    internal var mView:View
    private var mClickListener: ClickListener? = null
    init{
        mView = itemView
        itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                mClickListener!!.onItemClick(view, adapterPosition)
            }
        })
        itemView.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(view:View):Boolean {
                mClickListener!!.onItemLongClick(view, adapterPosition)
                return true
            }
        })
    }

    fun setDetails(ctx: Context, produk:String, toko:String, harga:String, lokasi:String, gambar:String) {
        val mProduk = mView.findViewById(R.id.namaProduk) as TextView
        val mToko = mView.findViewById(R.id.namaToko) as TextView
        val mHarga = mView.findViewById(R.id.hargaProduk) as TextView
        val mLokasi = mView.findViewById(R.id.lokasiProduk) as TextView
        val mGambar = mView.findViewById(R.id.imgProduk) as ImageView
        mProduk.text = produk
        mToko.text = toko
        mHarga.text = harga
        mLokasi.text = lokasi
        Picasso.get().load(gambar).into(mGambar)
    }

    interface ClickListener {
        fun onItemClick(view:View, position:Int)
        fun onItemLongClick(view:View, position:Int)
    }

    fun setOnClickListener(clickListener:ViewHolder.ClickListener) {
        mClickListener = clickListener
    }
}