package com.example.virtualsize.homes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsize.R
import com.example.virtualsize.adapter.AdapterProduk
import com.example.virtualsize.model.Produk
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listproduk.*

class ActivityListProduk : AppCompatActivity() {

    lateinit var list: ArrayList<Produk>
    lateinit var adapter: AdapterProduk
    lateinit var mLayoutManager: LinearLayoutManager //for sorting
    lateinit var mRecyclerView: RecyclerView
    lateinit var textbarProduk: TextView
    lateinit var jenis: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listproduk)

        jenis = intent.getStringExtra("jenis") as String

        setSupportActionBar(toolbarProduk)
        textbarProduk = findViewById(R.id.textbarProduk)
        textbarProduk.text = jenis

        list = arrayListOf()
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView = findViewById(R.id.recyclerProduk)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
        adapter = AdapterProduk(this, list)
        load()
    }

    private fun load(){
        val query = FirebaseDatabase.getInstance().getReference("Produk")
            .orderByChild("jenis").equalTo(jenis)
        query.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                if(p0.exists()){
                    p0.children.forEach {
                        val value = it.getValue(Produk::class.java)
                        if(value != null){
                            list.add(value)
                        }
                    }
                    mRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }

}
