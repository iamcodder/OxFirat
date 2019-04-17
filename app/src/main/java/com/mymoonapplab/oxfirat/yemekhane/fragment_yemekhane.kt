package com.mymoonapplab.oxfirat.yemekhane


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

import com.airbnb.lottie.LottieAnimationView
import com.mymoonapplab.oxfirat.R
import com.wang.avi.AVLoadingIndicatorView

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import java.io.IOException
import androidx.fragment.app.Fragment
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout


class fragment_yemekhane : Fragment() {


    private var textView_liste: TextView? = null
    private var textView_menu: TextView? = null
    private var textView_tarih: TextView? = null
    private var progressBar_pacman: AVLoadingIndicatorView? = null

    private var swipeRefresh_damla: WaveSwipeRefreshLayout? = null

    private var asynTask_yemekCek_object: asyncTask_yemekCek? = null

    private var rootview: View? = null

    private var no_internet_animation: LottieAnimationView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootview = inflater.inflate(R.layout.fragment_yemekhane, null)

        casting()
        swipe_refresh()



        return rootview
    }

    private fun casting() {
        asynTask_yemekCek_object = asyncTask_yemekCek()
        asynTask_yemekCek_object!!.execute()

        progressBar_pacman = rootview!!.findViewById(R.id.fragmentyemekhane_progress_avi)
        no_internet_animation = rootview!!.findViewById(R.id.fragment_yemekhane_no_internet_animation)
        no_internet_animation!!.pauseAnimation()

        textView_liste = rootview!!.findViewById(R.id.yemek_listesi)
        textView_menu = rootview!!.findViewById(R.id.menu)
        textView_tarih = rootview!!.findViewById(R.id.textView_tarihh)


    }

    private fun swipe_refresh() {
        swipeRefresh_damla = rootview!!.findViewById(R.id.fragment_yemekhane_waveswipe)
        swipeRefresh_damla!!.setOnRefreshListener { asyncTask_yemekCek().execute() }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class asyncTask_yemekCek : AsyncTask<Void, Void, Void>() {

        private var tarih: String? = null
        private var menu: String? = null
        private var yemeklerin_listesi: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
            tarih = null
            menu = null
            yemeklerin_listesi = ""
        }

        override fun doInBackground(vararg voids: Void): Void? {

            try {

                val document = Jsoup.connect(resources.getString(R.string.yemekhane_sitesi)).get()

                val elementsListe = document.select("div[class=views-field views-field-body]").select("p")

                for (i in elementsListe.indices) {
                    yemeklerin_listesi = yemeklerin_listesi!! + (elementsListe[i].text() + "\n")
                }

                val elementsMenu = document.select("div[class=views-field views-field-title]")
                menu = elementsMenu[0].text()

                val elementsTarih = document.select("span[class=field-content")
                tarih = elementsTarih[1].text()


            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)

            progressBar_pacman!!.smoothToHide()
            textView_menu!!.text = menu
            textView_liste!!.text = yemeklerin_listesi
            textView_tarih!!.text = tarih

            textView_menu!!.startAnimation(AnimationUtils.loadAnimation(context, R.anim.left_to_right))
            textView_liste!!.startAnimation(AnimationUtils.loadAnimation(context, R.anim.down_to_up))
            textView_tarih!!.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_left))

            if (textView_menu!!.text === "") {
                no_internet_animation!!.playAnimation()
                if (swipeRefresh_damla!!.isRefreshing) {
                    swipeRefresh_damla!!.isRefreshing = false
                }
            } else {
                no_internet_animation!!.pauseAnimation()
                if (swipeRefresh_damla!!.isRefreshing) {
                    swipeRefresh_damla!!.isRefreshing = false
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        if (asynTask_yemekCek_object != null && asynTask_yemekCek_object!!.cancel(true)) {
            asynTask_yemekCek_object = null
        }
        textView_menu!!.clearAnimation()
        textView_liste!!.clearAnimation()
        textView_tarih!!.clearAnimation()
    }
}
