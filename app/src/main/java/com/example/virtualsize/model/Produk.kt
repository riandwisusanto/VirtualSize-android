package com.example.virtualsize.model

import java.io.Serializable

data class Produk(
    val id:String = "",
    val jenis:String = "",
    val produk:String = "",
    val toko:String = "",
    val harga:String = "",
    val lokasi:String = "",
    val gambar:String = "",
    val rating:String = "",
    val deskripsi:String = ""
) : Serializable