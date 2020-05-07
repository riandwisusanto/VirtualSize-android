package com.example.virtualsize.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virtualsize.R
import com.example.virtualsize.login.ActivityLogin
import com.example.virtualsize.model.Sharepreference

class SplashScreen : AppCompatActivity() {

    val SP: Sharepreference = Sharepreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val backgrond = object : Thread(){
            override fun run() {
                try {
                    java.lang.Thread.sleep(2500)
                    if(SP.loadSP(applicationContext, "status").equals("login")){
                        val intent = android.content.Intent(applicationContext, ActivityUtama::class.java)
                        startActivity(intent)
                    }
                    else{
                        val intent = android.content.Intent(applicationContext, ActivityLogin::class.java)
                        startActivity(intent)
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        backgrond.start()
    }
}
