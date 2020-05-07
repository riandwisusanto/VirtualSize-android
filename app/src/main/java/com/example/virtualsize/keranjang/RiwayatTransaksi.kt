package com.example.virtualsize.keranjang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsize.R
import com.example.virtualsize.adapter.AdapterKerajang
import com.example.virtualsize.adapter.AdapterProduk
import com.example.virtualsize.model.Keranjang
import com.example.virtualsize.model.Sharepreference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_riwayat_transaksi.*

class RiwayatTransaksi : AppCompatActivity() {

    val SP = Sharepreference()
    lateinit var list: ArrayList<Keranjang>
    lateinit var adapter: AdapterKerajang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_transaksi)

        list = arrayListOf()
        listriwayat.setHasFixedSize(true)
        listriwayat.layoutManager = LinearLayoutManager(this)
        adapter = AdapterKerajang(this, list)
        load()

    }

    private fun load(){
        val ref = FirebaseDatabase.getInstance().getReference("Keranjang")
            .orderByChild("id_pembeli").equalTo(SP.loadSP(this, "id"))
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                if(p0.exists()){
                    p0.children.forEach {
                        val value = it.getValue(Keranjang::class.java)
                        if(value != null){
                            if(value.status == "diterima"){
                                list.add(value)
                            }
                        }
                    }
                    listriwayat.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }
}
