package com.example.virtualsize.akun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.virtualsize.model.Akun
import com.example.virtualsize.R
import com.example.virtualsize.model.Sharepreference
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ActivityProfil : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var textNama:TextView
    lateinit var textEmail:TextView
    lateinit var textAlamat:TextView
    lateinit var textNo:TextView
    lateinit var imgProfil:ImageView
    val SP = Sharepreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        textNama = findViewById(R.id.textNama)
        textEmail = findViewById(R.id.textEmail)
        textAlamat = findViewById(R.id.textAlamat)
        textNo = findViewById(R.id.textNo)
        imgProfil = findViewById(R.id.imgProfil)

        databaseReference = FirebaseDatabase.getInstance().getReference("Pengguna")
        val query = databaseReference.child(SP.loadSP(this, "id"))
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot.exists())
                {
                        val allocation = datasnapshot.getValue(Akun::class.java)
                        textNama.text = allocation!!.nama
                        textEmail.text = allocation.email
                        textAlamat.text = allocation.alamat
                        textNo.text = allocation.telp
                        Picasso.get().load(allocation.gambar).into(imgProfil)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
