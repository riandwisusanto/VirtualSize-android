package com.example.virtualsize.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsize.R
import com.example.virtualsize.homes.ActivityListProduk
import com.example.virtualsize.keranjang.ActivityPembayaran
import com.example.virtualsize.model.Keranjang
import com.example.virtualsize.keranjang.terima_pesanan
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class AdapterKerajang(val context: Context, val List : ArrayList<Keranjang>) :
    RecyclerView.Adapter<AdapterKerajang.Holder>(){

    inner class Holder(view: View?) : RecyclerView.ViewHolder(view!!),
        View.OnClickListener{

        val mProduk = view?.findViewById(R.id.namaProduk) as TextView
        val mToko = view?.findViewById(R.id.namaToko) as TextView
        val mHarga = view?.findViewById(R.id.hargaProduk) as TextView
        val mLokasi = view?.findViewById(R.id.lokasiProduk) as TextView
        val mGambar = view?.findViewById(R.id.imgProduk) as ImageView
        val hapus = view?.findViewById(R.id.hapus) as ImageView
        val tambah = view?.findViewById(R.id.tambahbelanja) as ImageView
        val jumlah = view?.findViewById(R.id.nominal) as TextView
        val prapesan = view?.findViewById(R.id.prapesan) as LinearLayout
        val pesanan = view?.findViewById(R.id.pesanan) as LinearLayout
        val jumlah_barang = view?.findViewById(R.id.jumlah_barang) as TextView
        val status = view?.findViewById(R.id.status) as TextView

        fun bind(context: Context, list: Keranjang){
            mProduk.text = list.produk
            mToko.text = list.toko
            mHarga.text = list.harga
            mLokasi.text = list.lokasi
            Picasso.get().load(list.gambar).into(mGambar)
            jumlah.setText(list.jumlah.toString())

            if(list.status != "x"){
                prapesan.visibility = View.GONE
                pesanan.visibility = View.VISIBLE
                jumlah_barang.text = "Jumlah : " + list.jumlah.toString()
                status.text = "Status : " + list.status
            }
            else{
                prapesan.visibility = View.VISIBLE
                pesanan.visibility = View.GONE
            }

            hapus.setOnClickListener {
                val query = FirebaseDatabase.getInstance().getReference("Keranjang")
                query.child(list.id).removeValue().addOnCompleteListener {
                    Toast.makeText(context.applicationContext, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
            }

            tambah.setOnClickListener {
                val intent = Intent(context, ActivityListProduk::class.java)
                intent.putExtra("jenis", list.jenis)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            itemView.setOnClickListener {
                if(list.status != "x" && list.status != "diterima"){
                    val bundle = Bundle()
                    bundle.putSerializable("list", list)
                    val intent = Intent(context, terima_pesanan::class.java)
                    intent.putExtras(bundle)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
                else if(list.status != "diterima"){
                    val bundle = Bundle()
                    bundle.putSerializable("list", list)
                    val intent = Intent(context, ActivityPembayaran::class.java)
                    intent.putExtras(bundle)
                    intent.putExtra("jumlah", jumlah.text.toString())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }

        }

        override fun onClick(v: View?) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_keranjang, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(context, List[position])
    }
}