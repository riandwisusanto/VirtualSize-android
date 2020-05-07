package com.example.virtualsize.login

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.virtualsize.model.Akun
import com.example.virtualsize.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*

class ActivityRegister : AppCompatActivity() {

    lateinit var btnDaftar: Button
    lateinit var textNama: EditText
    lateinit var textEmail: EditText
    lateinit var textPassword: EditText
    lateinit var textAlamat: EditText
    lateinit var textTelp: EditText
    lateinit var loading: View
    lateinit var databaseReference: DatabaseReference
    lateinit var uri: Uri
    lateinit var storageReference: StorageReference
    var url:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        textNama = findViewById(R.id.textNama)
        textEmail = findViewById(R.id.textEmail)
        textPassword = findViewById(R.id.textPassword)
        textAlamat = findViewById(R.id.textAlamat)
        textTelp = findViewById(R.id.textTelp)
        btnDaftar = findViewById(R.id.btnDaftar)
        loading = findViewById(R.id.loading)

        btnLogin.setOnClickListener {
            val intent = Intent(this@ActivityRegister, ActivityLogin::class.java)
            startActivity(intent)
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Pengguna")
        btnDaftar.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                loading.visibility = View.VISIBLE
                addData()
            }
        })

        storageReference = FirebaseStorage.getInstance().getReference("Pengguna")
        imgRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            if(requestCode == 0) {
                uri = data!!.data!!

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

                imgRegister.setImageBitmap(bitmap)

                val mStorage = storageReference.child(uri.lastPathSegment!!)
                try {
                    mStorage.putFile(uri).addOnFailureListener {}.addOnSuccessListener {
                            taskSnapshot -> mStorage.downloadUrl.addOnCompleteListener { taskSnapshot ->
                        url = taskSnapshot.result
                    }}
                } catch(ex:Exception) {
                    Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }}
    }

    private fun addData() {
        val nama = textNama.text.toString().trim()
        val email = textEmail.text.toString().trim()
        val password = textPassword.text.toString().trim()
        val alamat = textAlamat.text.toString().trim()
        val telp = textTelp.text.toString().trim()
        val gambar = url.toString()
        val id = databaseReference.push().key.toString()

        if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
            && !TextUtils.isEmpty(alamat) && !TextUtils.isEmpty(telp) && !TextUtils.isEmpty(gambar))
        {
            val add = Akun(
                id,
                nama,
                email,
                password,
                alamat,
                telp,
                gambar
            )

            databaseReference.child(id).setValue(add).addOnCompleteListener {
                loading.visibility = View.GONE
                Toast.makeText(this@ActivityRegister, "Akun Berhasil Dibuat", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        else
        {
            Toast.makeText(this@ActivityRegister, "Data Masih Kosong", Toast.LENGTH_LONG).show()
        }
    }
}
