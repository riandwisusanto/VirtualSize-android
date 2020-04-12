package com.example.virtualsize

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listproduk.*
import kotlinx.android.synthetic.main.card_produk.*
import java.io.ByteArrayOutputStream

class ActivityListProduk : AppCompatActivity() {

    lateinit var mLayoutManager: LinearLayoutManager //for sorting
    lateinit var mSharedPref: SharedPreferences //for saving sort settings
    lateinit var mRecyclerView: RecyclerView
    lateinit var databaseReference: DatabaseReference
    lateinit var textbarArmada: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listproduk)

        setSupportActionBar(toolbarProduk)
        textbarArmada = findViewById(R.id.textbarProduk) as TextView
        databaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        val query = databaseReference.child(intent.getStringExtra("jenis"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.getChildren())
                    {
                        val allocation = snapshot1.getValue(ListProduk::class.java)
                        textbarArmada.setText(allocation!!.jenis)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

//        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE)
//        val mSorting = mSharedPref.getString("Sort", "a-z")
//        if (mSorting == "a-z")
//        {
//            mLayoutManager = LinearLayoutManager(this)
//            mLayoutManager.setReverseLayout(true)
//            mLayoutManager.setStackFromEnd(true)
//        }
//        else if (mSorting == "z-a")
//        {
//            mLayoutManager = LinearLayoutManager(this)
//            mLayoutManager.setReverseLayout(false)
//            mLayoutManager.setStackFromEnd(false)
//        }

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView = findViewById(R.id.recyclerProduk)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
    }

//    private fun firebaseSearch(searchText:String) {
//        val firebaseSearchQuery = FirebaseDatabase.getInstance().getReference("Produk").child(intent.getStringExtra("jenis")).orderByChild("produk").startAt(searchText).endAt(searchText + "\uf8ff")
//        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<ListProduk, ViewHolder>(
//            ListProduk::class.java,
//            R.layout.card_produk,
//            ViewHolder::class.java,
//            firebaseSearchQuery
//        ) {
//            override fun populateViewHolder(viewHolder:ViewHolder, model:ListProduk, position:Int) {
//                viewHolder.setDetails(getApplicationContext(), model.produk, model.toko, model.harga, model.lokasi, model.gambar)
//            }
//            override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):ViewHolder {
//                val viewHolder = super.onCreateViewHolder(parent, viewType)
//                viewHolder.setOnClickListener(object: ViewHolder.ClickListener {
//                    override fun onItemClick(view: View, position:Int) {
//
//                        val namaProduk = view.findViewById(R.id.namaProduk) as TextView
//                        val namaToko = view.findViewById(R.id.namaToko) as TextView
//                        val hargaProduk = view.findViewById(R.id.hargaProduk) as TextView
//                        val lokasiProduk = view.findViewById(R.id.lokasiProduk) as TextView
//                        val imgProduk = view.findViewById(R.id.imgProduk) as ImageView
//
//                        val namaP = namaProduk.getText().toString()
//                        val nToko = namaToko.getText().toString()
//                        val hargaP = hargaProduk.getText().toString()
//                        val lokasiP = lokasiProduk.getText().toString()
//                        val imgP = imgProduk.getDrawable()
//                        val bitmap1 = (imgP as BitmapDrawable).getBitmap()
//
//                        val intent = Intent(view.getContext(), ActivityDetail::class.java)
//                        val stream = ByteArrayOutputStream()
//                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                        val bytes = stream.toByteArray()
//                        intent.putExtra("gambar", bytes)
//                        intent.putExtra("namaProduk", namaP)
//                        intent.putExtra("namaToko", nToko)
//                        intent.putExtra("hargaProduk", hargaP)
//                        intent.putExtra("lokasiProduk", lokasiP)
//                        startActivity(intent)
//                    }
//                    override fun onItemLongClick(view:View, position:Int) {
//
//                    }
//                })
//                return viewHolder
//            }
//        }
//        //set adapter to recyclerview
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter)
//    }

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
//    override fun onCreateOptionsMenu(menu: Menu):Boolean {
//        getMenuInflater().inflate(R.menu.menu_produk, menu)
//        val item = menu.findItem(R.id.action_search)
//        val searchView = item.getActionView() as SearchView
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query:String):Boolean {
//                firebaseSearch(query)
//                return false
//            }
//            override fun onQueryTextChange(newText:String):Boolean {
//                firebaseSearch(newText)
//                return false
//            }
//        })
//        return super.onCreateOptionsMenu(menu)
//    }
//    override fun onOptionsItemSelected(item: MenuItem):Boolean {
//        val id = item.getItemId()
//        if (id == R.id.action_sort)
//        {
//            showSortDialog()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//    private fun showSortDialog() {
//        val sortOptions = arrayOf<String>(" A-Z", " Z-A")
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Sort by")
//            .setIcon(R.drawable.icon_sort)
//            .setItems(sortOptions, object: DialogInterface.OnClickListener {
//                override fun onClick(dialog:DialogInterface, which:Int) {
//                    if (which == 0)
//                    {
//                        val editor = mSharedPref.edit()
//                        editor.putString("Sort", "a-z")
//                        editor.apply()
//                        recreate() //restart activity to take effect
//                    }
//                    else if (which == 1)
//                    {
//                        run {
//                            val editor = mSharedPref.edit()
//                            editor.putString("Sort", "z-a")
//                            editor.apply()
//                            recreate() //restart activity to take effect
//                        }
//                    }
//                }
//            })
//        builder.show()
//    }
}
