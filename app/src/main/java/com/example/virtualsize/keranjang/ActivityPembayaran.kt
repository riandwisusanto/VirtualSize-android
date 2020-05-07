package com.example.virtualsize.keranjang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.virtualsize.R
import com.example.virtualsize.model.Akun
import com.example.virtualsize.model.Keranjang
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pembayaran.*

class ActivityPembayaran : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        val list = intent.getSerializableExtra("list") as Keranjang

        val ref = FirebaseDatabase.getInstance().getReference("Pengguna")
            .child(list.id_pembeli)
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val value = p0.getValue(Akun::class.java)
                    namapengiriman.text = value!!.nama
                    nohppengiriman.text = value.telp
                    alamatpengiriman.text = value.alamat
                }
            }

        })

        Picasso.get().load(list.gambar).into(fotobarang)
        namabarang.text = list.produk
        harga.text = list.harga
        jumlah.text = intent.getStringExtra("jumlah") as String

        //set jasa kirim
        var jasakirim = ""
        val db = arrayListOf<String>("JNE", "J&T")
        val adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, db)
        pengiriman.adapter = adapter
        pengiriman.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                jasakirim = db[position]
                if(jasakirim == "JNE"){
                    ongkir.text = "10000"
                    subtotalppengiriman.text = "Rp. 10000,-"
                }
                else{
                    ongkir.text = "9000"
                    subtotalppengiriman.text = "Rp. 9000,-"
                }
                val tot = (list.harga.toInt() * jumlah.text.toString().toInt()) + ongkir.text.toString().toInt()
                total.text = "Rp. " + tot.toString() + ",-"
            }
        }

        subtotalproduk.text = "Rp. " + list.harga + ",-"

        buatpesanan.setOnClickListener {
            val reff = FirebaseDatabase.getInstance().getReference("Keranjang")
            val value = Keranjang(list.id, list.id_barang, list.id_pembeli, list.produk, list.toko, list.lokasi, list.gambar, list.harga
                , jumlah.text.toString().toInt(), list.jenis, jasakirim, subtotalppengiriman.text.toString()
                , "Bayar Ditempat", total.text.toString(), "menunggu")
            reff.child(list.id).setValue(value).addOnCompleteListener {
                Toast.makeText(this, "Pesanan Dibuat", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
