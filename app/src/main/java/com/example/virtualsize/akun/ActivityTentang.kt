package com.example.virtualsize.akun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virtualsize.R
import me.biubiubiu.justifytext.library.JustifyTextView

class ActivityTentang : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)

        val label = ("PENGGUNAAN APLIKASI INI MENUNJUKKAN PENERIMAAN DAN KEPATUHAN TERHADAP SYARAT DAN KETENTUAN DIBAWAH INI.\n" +
                "\n" +
                "       Selamat datang di Aplikasi VIRTUAL SIZE, suatu aplikasi E-Commerce atau Belanja Online yang menyediakan Perlengkapan Gadget berbasis Android dimana dalam aplikasi ini terdapat fitur unggulan, yaitu untuk mengetahui bentuk fisik dari barang tersebut dengan menggunakan teknologi Augmented Reality (AR).\n" +
                "       Syarat-syarat dan ketentuan penggunaan berikut ini berlaku dalam mengakses, memasang, dan atau menggunakan Aplikasi. Harap diketahui bahwa kami dapat mengubah, memodifikasi, menambah dan menghapus Syarat Penggunaan ini sewaktu-waktu tanpa pemberitahuan sebelumnya. Syarat Penggunaan harus dibaca secara berkala. Dengan terus menggunakan Aplikasi ini setelah perubahan tersebut terhadap Syarat Penggunaan, pengguna terdaftar (baik sebagai dosen ataupun mahasiswa) sepakat dan menyetujui perubahan. Jika Anda menggunakan layanan, maka penggunaan Anda didasarkan pada kepatuhan dan penerimaan terhadap syarat dan ketentuan yang berlaku untuk layanan tersebut.\n" +
                "\n" +
                "PRIVASI\n" +
                "       Kami menghargai kerahasiaan Pengguna. Kami akan berusaha keras untuk mematuhi persyaratan perundangan perlindungan data yang relevan saat melakukan kewajibannya menurut Syarat Penggunaan ini.\n" +
                "\n" +
                "HUKUM YANG MENGATUR\n" +
                "       Syarat Penggunaan ini akan diatur oleh dan ditafsirkan sesuai dengan peraturan yang berlaku di Indonesia.\n" +
                "\n" +
                "KETENTUAN\n" +
                "       Aplikasi ini merupakan aplikasi yang bersifat global area. Jika Anda memiliki pertanyaan lebih lanjut ataupun keluhan, harap menghubungi Admin.\n")
        val txtLabel = findViewById(R.id.textBantuan) as JustifyTextView
        txtLabel.text = label
    }
}