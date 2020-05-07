package com.example.virtualsize.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.virtualsize.home.ActivityUtama
import com.example.virtualsize.model.Akun
import com.example.virtualsize.R
import com.example.virtualsize.model.Sharepreference
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    lateinit var alertDialog: AlertDialog.Builder
    lateinit var btnLogin: Button
    lateinit var textEmail: EditText
    lateinit var textPassword:EditText
    lateinit var loading: View
    lateinit var databaseReference: DatabaseReference
    val SP: Sharepreference = Sharepreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnDaftar.setOnClickListener {
            val intent = Intent(this@ActivityLogin, ActivityRegister::class.java)
            startActivity(intent)
        }

        alertDialog = AlertDialog.Builder(this)
        textEmail = findViewById(R.id.textEmail)
        textPassword = findViewById(R.id.textPassword)
        btnLogin = findViewById(R.id.btnLogin)
        loading = findViewById(R.id.loading)
        btnLogin.setOnClickListener(this)
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    override fun onClick(view: View) {
        if (view == btnLogin)
        {
            loading.visibility = View.VISIBLE
            registerAkun()
        }
    }

    private fun registerAkun() {

        val query = databaseReference.child("Pengguna").orderByChild("email").equalTo(textEmail.text.toString().trim())
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())
                {
                    dataSnapshot.children.forEach {
                        val usersBean = it.getValue(Akun::class.java)
                        if(usersBean != null){
                            if (usersBean.password.equals(textPassword.text.toString().trim()))
                            {
                                val intent = Intent(applicationContext, ActivityUtama::class.java)
                                SP.createSP(applicationContext, "status", "login")
                                SP.createSP(applicationContext, "id", usersBean.id)
                                startActivity(intent)
                            }
                            else
                            {
                                loading.visibility = View.GONE
                                Toast.makeText(this@ActivityLogin, "Password is wrong", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                else
                {
                    loading.visibility = View.GONE
                    Toast.makeText(this@ActivityLogin, "User not found", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        Toast.makeText(this@ActivityLogin, "Back is Clicked", Toast.LENGTH_SHORT).show()
        alertDialog.setTitle("Close Application")
        alertDialog.setMessage("Do you want to close the application ?")
            .setCancelable(false)
            .setPositiveButton("YES", object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, id:Int) {
                    finishAffinity()
                }
            })
            .setNegativeButton("NO", object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, id:Int) {
                    dialog.cancel()
                }
            }).create().show()
    }
}
