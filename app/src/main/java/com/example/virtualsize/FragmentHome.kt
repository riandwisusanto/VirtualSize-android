package com.example.virtualsize

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        menu1.setOnClickListener {
            val intent = Intent(activity, ActivityListProduk::class.java)
            intent.putExtra("jenis", jenisProduk1.text.toString())
            startActivity(intent)
        }

        //List Promo
        val listPromo = view!!.findViewById<View>(R.id.listPromo) as RecyclerView
        val image = intArrayOf(R.drawable.beranda_card1, R.drawable.beranda_card2, R.drawable.beranda_card3)
        val manager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        listPromo.setLayoutManager(manager)
        listPromo.adapter = AdapterPromo(image, this.requireContext())
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(listPromo)

    }

}
