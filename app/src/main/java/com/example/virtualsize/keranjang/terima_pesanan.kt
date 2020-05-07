package com.example.virtualsize.keranjang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.virtualsize.R
import com.example.virtualsize.model.Keranjang
import com.example.virtualsize.model.UlasanModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_terima_pesanan.*

class terima_pesanan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terima_pesanan)

        val list = intent.getSerializableExtra("list") as Keranjang

        btn_back.setOnClickListener {
            finish()
        }

        Picasso.get().load(list.gambar).into(fotobarang)
        namabarang.text = list.produk
        status.text = list.status
        harga.text = "Rp. " + list.harga + ",-"
        jumlah.text = list.jumlah.toString()

        terima.setOnClickListener {
            beribintang.visibility = View.VISIBLE
        }

        pesananselesai.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Keranjang")
                .child(list.id)
            ref.child("status").setValue("diterima").addOnCompleteListener {
                val reef = FirebaseDatabase.getInstance().getReference("Ulasan_barang")
                val id = reef.push().key.toString()
                val value = UlasanModel(id, list.id, list.id_barang, list.id_pembeli, rating.numStars.toString(), ulasan.text.toString())
                reef.child(id).setValue(value).addOnCompleteListener {
                    kalkulasibintang(list.id_barang)
                }
                Toast.makeText(this, "Pesanan Diterima, Terimakasi sudah berbelanja", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun kalkulasibintang(idbarang: String){
        val ref = FirebaseDatabase.getInstance().getReference("Ulasan_barang").orderByChild("id_barang").equalTo(idbarang)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var x = 0
                    var y = 0
                    p0.children.forEach{
                        val value = it.getValue(UlasanModel::class.java)
                        if(value != null){
                            x = x + value.bintang.toString().toInt()
                            y++
                        }
                    }
                    val rate = (x / y).toFloat()
                    FirebaseDatabase.getInstance().getReference("Produk").child(idbarang).child("rating").setValue(rate.toString())
                }
            }

        })
    }
}
