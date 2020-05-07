package com.example.virtualsize.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsize.model.Keranjang
import com.example.virtualsize.R
import com.example.virtualsize.adapter.AdapterKerajang
import com.example.virtualsize.keranjang.RiwayatTransaksi
import com.example.virtualsize.model.Sharepreference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentKeranjang : Fragment() {

    lateinit var list: ArrayList<Keranjang>
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var adapter: AdapterKerajang
    lateinit var riwayat: Button
    val SP = Sharepreference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_keranjang, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView = view!!.findViewById(R.id.listKeranjang)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(mLayoutManager)
        list = arrayListOf()
        mRecyclerView = view!!.findViewById(R.id.listKeranjang)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(context))
        adapter = AdapterKerajang(context!!.applicationContext, list)
        load()

        riwayat = view!!.findViewById(R.id.riwayat)
        riwayat.setOnClickListener {
            val intent = Intent(context!!.applicationContext, RiwayatTransaksi::class.java)
            startActivity(intent)
        }
    }

    private fun load(){
        val query = FirebaseDatabase.getInstance().getReference("Keranjang")
            .orderByChild("id_pembeli").equalTo(SP.loadSP(context!!.applicationContext, "id"))
        query.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                if(p0.exists()){
                    p0.children.forEach {
                        val value = it.getValue(Keranjang::class.java)
                        if(value != null){
                            if(value.status != "diterima"){
                                list.add(value)
                            }
                        }
                    }
                    mRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }
}
