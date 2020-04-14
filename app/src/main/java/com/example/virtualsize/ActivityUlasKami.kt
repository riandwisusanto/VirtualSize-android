package com.example.virtualsize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*

class ActivityUlasKami : AppCompatActivity() {

    lateinit var btnUlas: Button
    lateinit var textNama: TextView
    lateinit var textEmail: TextView
    lateinit var inputUlasan: EditText
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ulaskami)

        textNama = findViewById(R.id.textNama)
        textEmail = findViewById(R.id.textEmail)
        btnUlas = findViewById(R.id.btnUlas)
        inputUlasan = findViewById(R.id.inputUlasan)

        databaseReference = FirebaseDatabase.getInstance().getReference()
        val query = databaseReference.child("Pengguna").orderByChild("nama").equalTo(intent.getStringExtra("nama"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.children)
                    {
                        val allocation = snapshot1.getValue(Akun::class.java)
                        textNama.setText(allocation!!.nama)
                        textEmail.setText(allocation!!.email)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        databaseReference = FirebaseDatabase.getInstance().getReference("Ulasan")
        btnUlas.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                addData()
            }
        })
    }

    private fun addData() {
        val nama = textNama.text.toString().trim()
        val email = textEmail.text.toString().trim()
        val ulasan = inputUlasan.text.toString().trim()
        if (!TextUtils.isEmpty(ulasan))
        {
            val ulas = UlasKami(nama, email, ulasan)
            databaseReference.child(nama).setValue(ulas)
            inputUlasan.setText("")
            Toast.makeText(this@ActivityUlasKami, "Data Terkirim", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this@ActivityUlasKami, "Data Masih Kosong", Toast.LENGTH_LONG).show()
        }
    }
}
