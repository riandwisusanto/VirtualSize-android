package com.example.virtualsize

class DetailProduk {
    lateinit var produk:String
    lateinit var toko:String
    lateinit var harga:String
    lateinit var lokasi:String
    lateinit var deskripsi:String
    lateinit var gambar:String

    constructor(){}
    constructor(produk:String, toko:String, harga:String, lokasi:String, deskripsi:String, gambar:String){
        this.produk = produk
        this.toko = toko
        this.harga = harga
        this.lokasi = lokasi
        this.deskripsi = deskripsi
        this.gambar = gambar
    }
}