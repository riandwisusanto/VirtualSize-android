package com.example.virtualsize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        namaProduk = findViewById(R.id.namaProduk)
        namaToko = findViewById(R.id.namaToko)
        hargaProduk = findViewById(R.id.hargaProduk)
        lokasiProduk = findViewById(R.id.lokasiProduk)
        textDeskripsi = findViewById(R.id.textDeskripsi)
        imgProduk = findViewById(R.id.gambarDetail)

        databaseReference = FirebaseDatabase.getInstance().getReference()
        val query = databaseReference.child("Detail Produk").orderByChild("produk").equalTo(intent.getStringExtra("namaProduk"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(DetailProduk::class.java)
                        namaProduk.text = allocation!!.produk
                        namaToko.text = allocation.toko
                        hargaProduk.text = allocation.harga
                        lokasiProduk.text = allocation.lokasi
                        textDeskripsi.text = allocation.deskripsi
                        Picasso.get().load(allocation.gambar).into(imgProduk)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        databaseReference = FirebaseDatabase.getInstance().getReference("Keranjang")
        btnTambahKeranjang.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                val produk = namaProduk.text.toString().trim()
                val toko = namaToko.text.toString().trim()
                val harga = hargaProduk.text.toString().trim()
                val lokasi = lokasiProduk.text.toString().trim()
                val gambar = imgProduk.toString().trim()
                if (!TextUtils.isEmpty(produk) && !TextUtils.isEmpty(toko) && !TextUtils.isEmpty(lokasi)
                    && !TextUtils.isEmpty(harga) && !TextUtils.isEmpty(gambar))
                {
                    val keranjang = Keranjang(produk, toko, harga, lokasi, gambar)
                    databaseReference.child(produk).setValue(keranjang)
                    Toast.makeText(this@ActivityDetail, "Data Terkirim", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this@ActivityDetail, "Data Masih Kosong", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
