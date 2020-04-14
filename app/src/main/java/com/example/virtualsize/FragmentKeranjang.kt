package com.example.virtualsize

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class FragmentKeranjang : Fragment() {

    lateinit var mLayoutManager: LinearLayoutManager //for sorting
    lateinit var mSharedPref: SharedPreferences //for saving sort settings
    lateinit var mRecyclerView: RecyclerView
    lateinit var databaseReference: DatabaseReference
    lateinit var textbarArmada: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keranjang, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = view!!.findViewById(R.id.listKeranjang)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Keranjang")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Keranjang, ViewHolder>(
            Keranjang::class.java,
            R.layout.card_keranjang,
            ViewHolder::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewHolder, model:Keranjang, position:Int) {
                viewHolder.setDetails(activity!!.getApplicationContext(), model.produk, model.toko, model.harga, model.lokasi, model.gambar)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
                    override fun onItemClick(view:View, position:Int) {

                        val namaProduk = view.findViewById(R.id.namaProduk) as TextView
                        val namaP = namaProduk.text.toString()
                        val intent = Intent(view.context, ActivityPembayaran::class.java)
//                        intent.putExtra("namaProduk", namaP)
                        startActivity(intent)

//                        tambahKeranjang.setOnClickListener {
//                            val add = ListProduk(jenis, produk, toko, harga, lokasi, gambar)
//                            databaseReference.child(nama).setValue(add)
//                        }
                    }
                    override fun onItemLongClick(view:View, position:Int) {

                    }
                })
                return viewHolder
            }
        }
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
    }
}
