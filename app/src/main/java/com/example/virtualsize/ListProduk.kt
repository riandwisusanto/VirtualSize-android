package com.example.virtualsize

class ListProduk {
    lateinit var jenis:String
    lateinit var produk:String
    lateinit var toko:String
    lateinit var harga:String
    lateinit var lokasi:String
    lateinit var gambar:String

    constructor(){}
    constructor(jenis:String, produk:String, toko:String, harga:String, lokasi:String, gambar:String){
        this.jenis = jenis
        this.produk = produk
        this.toko = toko
        this.harga = harga
        this.lokasi = lokasi
        this.gambar = gambar
    }
}