package com.example.virtualsize.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsize.R
import com.example.virtualsize.homes.ActivityDetail
import com.example.virtualsize.keranjang.ActivityPembayaran
import com.example.virtualsize.model.Keranjang
import com.example.virtualsize.model.Produk
import com.example.virtualsize.model.Sharepreference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class AdapterProduk(val context: Context, val list : ArrayList<Produk>) :
    RecyclerView.Adapter<AdapterProduk.Holder>(){

    val SP = Sharepreference()

    inner class Holder(view: View?) : RecyclerView.ViewHolder(view!!),
        View.OnClickListener{

        val mProduk = view?.findViewById(R.id.namaProduk) as TextView
        val mToko = view?.findViewById(R.id.namaToko) as TextView
        val mHarga = view?.findViewById(R.id.hargaProduk) as TextView
        val mLokasi = view?.findViewById(R.id.lokasiProduk) as TextView
        val mGambar = view?.findViewById(R.id.imgProduk) as ImageView
        val mRating = view?.findViewById(R.id.ratingProduk) as TextView
        val tambahkeranjang = view?.findViewById(R.id.tambahkeranjang) as TextView
        val jum_hit = view?.findViewById(R.id.jum_hit) as TextView
        val id_hit = view?.findViewById(R.id.id_hit) as TextView

        fun bind(context: Context, list: Produk){
            mProduk.text = list.produk
            mToko.text = list.toko
            mHarga.text = list.harga
            mLokasi.text = list.lokasi
            mRating.text = list.rating
            Picasso.get().load(list.gambar).into(mGambar)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("list", list)
                val intent = Intent(context, ActivityDetail::class.java)
                intent.putExtras(bundle)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            val databaseReference = FirebaseDatabase.getInstance().getReference("Keranjang")
            //cek keranjang
            databaseReference
                .orderByChild("id_pembeli").equalTo(SP.loadSP(context.applicationContext, "id"))
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()){
                            p0.children.forEach {
                                val value = it.getValue(Keranjang::class.java)
                                if(value != null){
                                    if(value.id_barang == list.id){
                                        jum_hit.text = value.jumlah.toString()
                                        id_hit.text = value.id
                                        println("id_barang = " + value.id_barang)
                                    }
                                }
                            }
                        }
                    }
                })

            tambahkeranjang.setOnClickListener {
                val produk = mProduk.text.toString().trim()
                val toko = mToko.text.toString().trim()
                val harga = mHarga.text.toString().trim()
                val lokasi = mLokasi.text.toString().trim()
                val gambar = mGambar.toString().trim()
                val id = databaseReference.push().key.toString()
                val keranjang = Keranjang(
                    id,
                    list.id,
                    SP.loadSP(context, "id"),
                    produk,
                    toko,
                    lokasi,
                    gambar,
                    harga,
                    1,
                    list.jenis,
                    "-",
                    "-",
                    "-",
                    "-",
                    "x"
                )
                if(jum_hit.text.toString() != "0"){
                    val jum = jum_hit.text.toString().toInt() + 1
                    println("id : " + id_hit.text.toString())
                    databaseReference.child(id_hit.text.toString())
                        .child("jumlah")
                        .setValue(jum)
                        .addOnCompleteListener {
                            Toast.makeText(context.applicationContext, "Barang ditambahkan", Toast.LENGTH_LONG).show()
                        }
                }
                else{
                    databaseReference.child(id).setValue(keranjang).addOnCompleteListener {
                        Toast.makeText(context.applicationContext, "Barang ditambahkan", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        override fun onClick(v: View?) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_produk, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(context, list[position])
    }
}