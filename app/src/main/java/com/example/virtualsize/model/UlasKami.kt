package com.example.virtualsize.model

class UlasKami {
    lateinit var nama:String
    lateinit var email:String
    lateinit var ulasan:String

    constructor() {}
    constructor(nama:String, email:String, ulasan:String) {
        this.nama = nama
        this.email = email
        this.ulasan = ulasan
    }
}