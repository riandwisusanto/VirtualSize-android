package com.example.virtualsize

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    }
}
