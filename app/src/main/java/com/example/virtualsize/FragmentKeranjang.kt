package com.example.virtualsize

import android.content.Intent
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

    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var mRef: DatabaseReference
    lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keranjang, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mLayoutManager = LinearLayoutManager(activity!!)

        mLayoutManager.setReverseLayout(false)
        mLayoutManager.setStackFromEnd(false)

        mRecyclerView = view!!.findViewById(R.id.listKeranjang)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mRef = mFirebaseDatabase.getReference("sony")
    }

    override fun onStart() {
        super.onStart()
        val query = mFirebaseDatabase.getReference("sony")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<ListProduk, ViewHolder>(
            ListProduk::class.java,
            R.layout.card_keranjang,
            ViewHolder::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewHolder, model:ListProduk, position:Int) {
                viewHolder.setDetails(activity!!.getApplicationContext(), model.produk, model.toko, model.harga, model.lokasi, model.gambar)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
//                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
//                    override fun onItemClick(view:View, position:Int) {
//                        //Views
//                        val mProduk = view.findViewById(R.id.namaProduk) as TextView
//                        val mToko = view.findViewById(R.id.namaToko) as TextView
//                        val mHarga = view.findViewById(R.id.hargaProduk) as TextView
//                        val mLokasi = view.findViewById(R.id.lokasiProduk) as TextView
//                        val mGambar = view.findViewById(R.id.imgProduk) as ImageView
//                        //get data from views
//                        val nProduk = mProduk.getText().toString()
//                        val nToko = mToko.getText().toString()
//                        val nHarga = mHarga.getText().toString()
//                        val nLokasi = mLokasi.getText().toString()
//                        val nGambar = mGambar.getDrawable()
//                        val mBitmap = (nGambar as BitmapDrawable).getBitmap()
//                        //pass this data to new activity
//                        val intent = Intent(view.getContext(), ActivityDetail::class.java)
//                        val stream = ByteArrayOutputStream()
//                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                        val bytes = stream.toByteArray()
//                        intent.putExtra("produk", nProduk) // put title
//                        intent.putExtra("toko", nToko) //put description
//                        intent.putExtra("harga", nHarga)
//                        intent.putExtra("lokasi", nLokasi)
//                        intent.putExtra("gambar", bytes) //put bitmap image as array of bytes
////                        intent.putExtra("email", textBarCari.getText().toString())
//                        startActivity(intent) //start activity
//                    }
//                    override fun onItemLongClick(view:View, position:Int) {
//                        //TODO do your own implementaion on long item click
//                    }
//                })
                return viewHolder
            }
        }
        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
    }
}
