package com.example.virtualsize.model

import android.content.Context
import android.content.SharedPreferences

class Sharepreference {
    lateinit var SP : SharedPreferences

    fun createSP(context: Context, name: String, value: String){
        SP = context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = SP.edit()
        editor.putString(name, value)
        editor.apply()
    }

    fun loadSP(context: Context, key: String) : String{
        SP = context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val value = SP.getString(key, "").toString()
        return value
    }

    fun createSParray(context: Context, name: String, value: MutableSet<String>){
        SP = context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = SP.edit()
        editor.putStringSet(name, value)
        editor.apply()
    }
}