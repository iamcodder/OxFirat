package com.mymoonapplab.oxfirat

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast

import com.airbnb.lottie.LottieAnimationView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.mymoonapplab.oxfirat.duyurular.fragment_duyurular
import com.mymoonapplab.oxfirat.etkinlik.fragment_etkinlik
import com.mymoonapplab.oxfirat.haberler.fragment_haberler
import com.mymoonapplab.oxfirat.yemekhane.fragment_yemekhane

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {


    private var res: Resources? = null

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        res = resources


        if (!InternetKontrol()) {
            Toast.makeText(this, res!!.getString(R.string.internet_acin), Toast.LENGTH_LONG).show()

        }


        val bottomNavigation = findViewById<MeowBottomNavigation>(R.id.main_activity_bottom_navigation_bar)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.newspaper))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.bullhorn))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.account_group))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_restaurant_menu_black_24dp))

        bottomNavigation.show(1, true)

        bottomNavigation.setOnClickMenuListener { model ->
            when (model.id) {

                1 -> fragment = fragment_haberler()
                2 -> fragment = fragment_duyurular()
                3 -> fragment = fragment_etkinlik()
                4 -> fragment = fragment_yemekhane()
            }
            load_fragment(fragment)
        }

        load_fragment(fragment_haberler())

    }


    fun InternetKontrol(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo != null && manager.activeNetworkInfo.isConnected
    }

    private fun load_fragment(fragment: Fragment?) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_tutucu_frameLayout, fragment!!)
                .commit()
    }


}
