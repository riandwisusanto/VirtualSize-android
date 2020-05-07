package com.example.virtualsize.home

import com.example.virtualsize.login.ActivityLogin
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.virtualsize.R
import com.example.virtualsize.akun.ActivityKontak
import com.example.virtualsize.akun.ActivityProfil
import com.example.virtualsize.akun.ActivityTentang
import com.example.virtualsize.akun.ActivityUlasKami
import com.example.virtualsize.model.Akun
import com.example.virtualsize.model.Sharepreference
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_akun.*

class FragmentAkun : Fragment() {
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var databaseReference: DatabaseReference
    lateinit var textNama: TextView
    lateinit var textEmail: TextView
    lateinit var imgAkun: ImageView
    val SP = Sharepreference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        alertDialog = AlertDialog.Builder(activity!!)

        lihatAkun.setOnClickListener {
            val intent = Intent(activity, ActivityProfil::class.java)
            intent.putExtra("nama", textNama.getText().toString())
            startActivity(intent)
        }
        akun1.setOnClickListener {
            val intent = Intent(activity, ActivityTentang::class.java)
            startActivity(intent)
        }
        akun2.setOnClickListener {
            val intent = Intent(activity, ActivityKontak::class.java)
            startActivity(intent)
        }
        akun3.setOnClickListener {
            val intent = Intent(activity, ActivityUlasKami::class.java)
            intent.putExtra("nama", textNama.getText().toString())
            intent.putExtra("email", textEmail.text.toString())
            startActivity(intent)
        }
        akun5.setOnClickListener {
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah Kamu Ingin Keluar dari Akun Ini ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog:DialogInterface, id:Int) {
                        val intent = Intent(activity, ActivityLogin::class.java)
                        startActivity(intent)
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog:DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }

        textNama = view!!.findViewById<View>(R.id.namaAkun) as TextView
        textEmail = view!!.findViewById<View>(R.id.emailAkun) as TextView
        imgAkun = view!!.findViewById<View>(R.id.imgAkun) as ImageView
        databaseReference = FirebaseDatabase.getInstance().getReference()
        val query = databaseReference.child("Pengguna")
            .child(SP.loadSP(context!!.applicationContext, "id"))
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot.exists())
                {
                        val allocation = datasnapshot.getValue(Akun::class.java)
                        textNama.text = allocation!!.nama
                        textEmail.text = allocation.email
                        Picasso.get().load(allocation.gambar).into(imgAkun)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
