package com.example.virtualsize

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper


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

        //List Promo
        val listPromo = view!!.findViewById<View>(R.id.listPromo) as RecyclerView
        val image = intArrayOf(R.drawable.beranda_card1, R.drawable.beranda_card2, R.drawable.beranda_card3)
        val manager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        listPromo.setLayoutManager(manager)
        listPromo.adapter = AdapterPromo(image, this.requireContext())
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(listPromo)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val w: Window = activity!!.getWindow()
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }

    }

}
