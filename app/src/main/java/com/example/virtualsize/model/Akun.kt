package com.example.virtualsize.model

class Akun {
    lateinit var id:String
    lateinit var nama:String
    lateinit var email:String
    lateinit var password:String
    lateinit var alamat:String
    lateinit var telp:String
    lateinit var gambar:String

    constructor() {}
    constructor(id: String, nama:String, email:String, password:String, alamat:String, telp:String, gambar:String) {
        this.id = id
        this.nama = nama
        this.email = email
        this.password = password
        this.alamat = alamat
        this.telp = telp
        this.gambar = gambar
    }
}