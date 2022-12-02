package com.faishal.saku.ui.foodspin

import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.util.luckydraw.LuckyWheel
import com.faishal.saku.util.luckydraw.OnLuckyWheelReachTheTarget
import com.faishal.saku.util.luckydraw.WheelItem
import java.util.*

class FoodSpinWheelActivity : BaseActivity() {

    @BindView(R.id.btn_back)
    lateinit var btnBack: ImageView

    @BindView(R.id.sw_food)
    lateinit var swFood: LuckyWheel

    @BindView(R.id.txt_makan)
    lateinit var txtMakan: TextView

    private var foodItem: MutableList<WheelItem> = ArrayList()
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_spin_wheel)
        ButterKnife.bind(this)
        setDataGacha()
        setView()

    }

    private fun setView() {
        swFood.setLuckyWheelReachTheTarget(object : OnLuckyWheelReachTheTarget {
            override fun onReachTarget() {
                val dataFood: WheelItem = foodItem.get(points - 1)
                val namaMakanan = dataFood.text
                txtMakan.setText("Yeay.. Sepertinya kamu akan makan '" + namaMakanan + "'")
                randomData()
                swFood.setTarget(points)
            }
        })
    }

    private fun setDataGacha() {
        foodItem.add(WheelItem(Color.parseColor("#fc6c6c"),
            BitmapFactory.decodeResource(resources, R.drawable.orange),
            "Ayam"))
        foodItem.add(WheelItem(Color.parseColor("#00E6FF"),
            BitmapFactory.decodeResource(resources, R.drawable.blank),
            "Bakso"))
        foodItem.add(WheelItem(Color.parseColor("#F00E6F"),
            BitmapFactory.decodeResource(resources, R.drawable.orange),
            "Tahu Tempe"))
        foodItem.add(WheelItem(Color.parseColor("#00E6FF"),
            BitmapFactory.decodeResource(resources, R.drawable.blank),
            "Mie"))
        foodItem.add(WheelItem(Color.parseColor("#fc6c6c"),
            BitmapFactory.decodeResource(resources, R.drawable.orange),
            "Telur"))
        foodItem.add(WheelItem(Color.parseColor("#00E6FF"),
            BitmapFactory.decodeResource(resources, R.drawable.blank),
            "Daging Sapi"))


        swFood.addWheelItems(foodItem, MediaPlayer.create(this, R.raw.luckyspin))

        randomData()
        swFood.setTarget(points)
    }

    private fun randomData() {
        val random = Random()
        points = random.nextInt(foodItem.size + 1)
        if (points == 0 || points == foodItem.size + 1) {
            points = 1
        }
    }

    @OnClick(R.id.btn_back)
    fun onBtnBackClicked() {
        onBackPressed()
    }
}