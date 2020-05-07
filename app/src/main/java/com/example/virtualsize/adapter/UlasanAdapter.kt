package com.example.virtualsize.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsize.R
import com.example.virtualsize.model.Akun
import com.example.virtualsize.model.UlasanModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UlasanAdapter(val context: Context, val List : ArrayList<UlasanModel>) :
    RecyclerView.Adapter<UlasanAdapter.Holder>(){

    inner class Holder(view: View?) : RecyclerView.ViewHolder(view!!),
        View.OnClickListener{

        val nama: TextView
        val rate: TextView
        val ulasan: TextView

        init {
            nama = view!!.findViewById(R.id.nama) as TextView
            rate = view.findViewById(R.id.rate) as TextView
            ulasan = view.findViewById(R.id.ulasantxt) as TextView
        }

        fun bind(list: UlasanModel, context: Context){
            val ref = FirebaseDatabase.getInstance().getReference("Pengguna")
                .child(list.id_pembeli)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        val value = p0.getValue(Akun::class.java)
                        if(value != null){
                            nama.setText(value.nama)
                        }
                    }
                }

            })

            rate.setText(list.bintang)
            ulasan.setText(list.ulasan)
        }

        override fun onClick(p0: View?) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_ulasan, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(List[position], context)
    }
}