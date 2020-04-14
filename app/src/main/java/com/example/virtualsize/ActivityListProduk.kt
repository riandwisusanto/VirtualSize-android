package com.example.virtualsize

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listproduk.*

class ActivityListProduk : AppCompatActivity() {

    lateinit var mLayoutManager: LinearLayoutManager //for sorting
    lateinit var mRecyclerView: RecyclerView
    lateinit var databaseReference: DatabaseReference
    lateinit var textbarProduk: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listproduk)

        setSupportActionBar(toolbarProduk)
        textbarProduk = findViewById(R.id.textbarProduk)
        databaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        val query = databaseReference.child(intent.getStringExtra("jenis"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(ListProduk::class.java)
                        textbarProduk.setText(allocation!!.jenis)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView = findViewById(R.id.recyclerProduk)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Produk").child(intent.getStringExtra("jenis"))
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<ListProduk, ViewHolder>(
            ListProduk::class.java,
            R.layout.card_produk,
            ViewHolder::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewHolder, model:ListProduk, position:Int) {
                viewHolder.setDetails(getApplicationContext(), model.produk, model.toko, model.harga, model.lokasi, model.gambar)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
                    override fun onItemClick(view:View, position:Int) {

                        val namaProduk = view.findViewById(R.id.namaProduk) as TextView
                        val namaP = namaProduk.text.toString()
                        val intent = Intent(view.context, ActivityDetail::class.java)
                        intent.putExtra("namaProduk", namaP)
                        startActivity(intent)

                    }
                    override fun onItemLongClick(view:View, position:Int) {

                    }
                })
                return viewHolder
            }
        }
        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
    }
}
