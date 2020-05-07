package com.example.virtualsize.homes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsize.Chat.MessageActivity
import com.example.virtualsize.R
import com.example.virtualsize.adapter.UlasanAdapter
import com.example.virtualsize.model.Keranjang
import com.example.virtualsize.model.Produk
import com.example.virtualsize.model.Sharepreference
import com.example.virtualsize.model.UlasanModel
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class ActivityDetail : AppCompatActivity() {

    lateinit var namaProduk: TextView
    lateinit var namaToko:TextView
    lateinit var hargaProduk: TextView
    lateinit var lokasiProduk: TextView
    lateinit var textDeskripsi: TextView
    lateinit var imgProduk: ImageView
    lateinit var databaseReference: DatabaseReference
    val SP = Sharepreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        namaProduk = findViewById(R.id.namaProduk)
        namaToko = findViewById(R.id.namaToko)
        hargaProduk = findViewById(R.id.hargaProduk)
        lokasiProduk = findViewById(R.id.lokasiProduk)
        textDeskripsi = findViewById(R.id.textDeskripsi)
        imgProduk = findViewById(R.id.gambarDetail)

        val list = intent.getSerializableExtra("list") as Produk

        namaProduk.text = list.produk
        namaToko.text = list.toko
        hargaProduk.text = list.harga
        lokasiProduk.text = list.lokasi
        textDeskripsi.text = list.deskripsi
        Picasso.get().load(list.gambar).into(imgProduk)
        ratingProduk.text = list.rating

        databaseReference = FirebaseDatabase.getInstance().getReference("Keranjang")

        //cek keranjang
        databaseReference
            .orderByChild("id_pembeli").equalTo(SP.loadSP(this@ActivityDetail, "id"))
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

        btnTambahKeranjang.setOnClickListener {
                val produk = namaProduk.text.toString().trim()
                val toko = namaToko.text.toString().trim()
                val harga = hargaProduk.text.toString().trim()
                val lokasi = lokasiProduk.text.toString().trim()
                val gambar = imgProduk.toString().trim()
                val id = databaseReference.push().key.toString()
                if (!TextUtils.isEmpty(produk) && !TextUtils.isEmpty(toko) && !TextUtils.isEmpty(lokasi)
                    && !TextUtils.isEmpty(harga) && !TextUtils.isEmpty(gambar))
                {
                    val keranjang = Keranjang(
                        id,
                        list.id,
                        SP.loadSP(this@ActivityDetail, "id"),
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
                                Toast.makeText(this@ActivityDetail, "Barang ditambahkan", Toast.LENGTH_LONG).show()
                                finish()
                            }
                    }
                    else{
                        databaseReference.child(id).setValue(keranjang).addOnCompleteListener {
                            Toast.makeText(this@ActivityDetail, "Barang ditambahkan", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                }
                else
                {
                    Toast.makeText(this@ActivityDetail, "Data Masih Kosong", Toast.LENGTH_LONG).show()
                }
            }

        chat.setOnClickListener {
            val intent = Intent(this, MessageActivity::class.java)
            intent.putExtra("id", "Apple")
            intent.putExtra("username", "Apple Store")
            intent.putExtra("foto", list.gambar)
            startActivity(intent)
        }

        var list_ulasan: ArrayList<UlasanModel> = arrayListOf()
        val adapter = UlasanAdapter(this, list_ulasan)
        recyclerProduk.setHasFixedSize(true)
        recyclerProduk.layoutManager = LinearLayoutManager(this)

        val raf = FirebaseDatabase.getInstance().getReference("Ulasan_barang")
            .orderByChild("id_barang").equalTo(list.id)
        raf.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list_ulasan.clear()
                if(p0.exists()){
                    p0.children.forEach {
                        val value = it.getValue(UlasanModel::class.java)
                        if(value != null){
                            list_ulasan.add(value)
                        }
                    }
                    recyclerProduk.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}
