package com.example.virtualsize.model

import java.io.Serializable

data class Keranjang(
    val id: String = "",
    val id_barang: String = "",
    val id_pembeli: String = "",
    val produk: String = "",
    val toko: String = "",
    val lokasi: String = "",
    val gambar: String = "",
    val harga: String = "",
    val jumlah: Int = 0,
    val jenis: String = "",
    val jasakirim: String = "",
    val ongkir: String = "",
    val metodebayar: String = "",
    val total: String = "",
    val status: String = ""
) : Serializable