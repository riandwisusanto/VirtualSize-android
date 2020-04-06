package com.example.virtualsize

class Keranjang {
    lateinit var user:String
    lateinit var produk:String
    lateinit var toko:String
    lateinit var harga:String
    lateinit var lokasi:String
    lateinit var gambar:String

    constructor(){}
    constructor(user:String, produk:String, toko:String, harga:String, lokasi:String, gambar:String){
        this.user = user
        this.produk = produk
        this.toko = toko
        this.harga = harga
        this.lokasi = lokasi
        this.gambar = gambar
    }
}