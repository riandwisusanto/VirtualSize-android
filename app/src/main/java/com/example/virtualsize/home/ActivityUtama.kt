package com.example.virtualsize.home

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.virtualsize.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_utama.*

class ActivityUtama : AppCompatActivity() {

    //Bottom Navigation
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home -> {
                replaceFragment(FragmentHome())
                return@OnNavigationItemSelectedListener true
            }
            R.id.keranjang -> {
                replaceFragment(FragmentKeranjang())
                return@OnNavigationItemSelectedListener true
            }
            R.id.akun -> {
                replaceFragment(FragmentAkun())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama)

        //Bottom Navigation
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(FragmentHome())
    }

    //Bottom Navigation
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        Toast.makeText(this, "Back is Clicked", Toast.LENGTH_SHORT).show()
        alertDialog.setTitle("Close Application")
        alertDialog.setMessage("Do you want to close the application ?")
            .setCancelable(false)
            .setPositiveButton("YES", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    finishAffinity()
                }
            })
            .setNegativeButton("NO", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    dialog.cancel()
                }
            }).create().show()
    }
}
