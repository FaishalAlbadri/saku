package com.faishal.saku.ui.foodspin

import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.databinding.ActivityFoodSpinWheelBinding
import com.faishal.saku.util.luckydraw.LuckyWheel
import com.faishal.saku.util.luckydraw.OnLuckyWheelReachTheTarget
import com.faishal.saku.util.luckydraw.WheelItem
import java.util.*

class FoodSpinWheelActivity : BaseActivity() {

    private var foodItem: MutableList<WheelItem> = ArrayList()
    private var points: Int = 0

    private val namaMakanan = arrayOf(
        "Cumi", "Telur", "Ayam",
        "Kerang", "Buah", "Udang", "Sate Kelinci",
        "Nasi Goreng", "Bubur", "Kepiting",
        "Bakso", "Soto", "Pizza",
        "Sayur",
        "Sapi", "Mie", "Ketoprak",
        "Roti", "Kambing", "Ikan"
    )
    private val warnaMakanan = arrayOf(
        "#F00E6F", "#00E6FF", "#fc6c6c",
        "#F00E6F", "#17CF61", "#F00E6F", "#EAD309",
        "#fc6c6c", "#00E6FF", "#F00E6F",
        "#00E6FF", "#fc6c6c", "#F00E6F",
        "#17CF61",
        "#F00E6F", "#00E6FF", "#fc6c6c",
        "#00E6FF", "#F00E6F", "#fc6c6c"
    )
    private var _binding: ActivityFoodSpinWheelBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodSpinWheelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDataGacha()
        setView()

    }

    private fun setView() {
        binding.apply {
            swFood.setLuckyWheelReachTheTarget(object : OnLuckyWheelReachTheTarget {
                override fun onReachTarget() {
                    val dataFood: WheelItem = foodItem.get(points - 1)
                    val namaMakanan = dataFood.text
                    txtMakan.setText("Yeay.. Sepertinya kamu akan makan '" + namaMakanan + "'")
                    randomData()
                    swFood.setTarget(points)
                }
            })

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setDataGacha() {
        for (i in 0..namaMakanan.size - 1) {
            foodItem.add(
                WheelItem(
                    Color.parseColor(warnaMakanan[i]),
                    namaMakanan[i]
                )
            )
        }

        binding.apply {
            swFood.addWheelItems(foodItem, MediaPlayer.create(this@FoodSpinWheelActivity, R.raw.luckyspin))

            randomData()
            swFood.setTarget(points)
        }
    }

    private fun randomData() {
        val random = Random()
        points = random.nextInt(foodItem.size + 1)
        if (points == 0 || points == foodItem.size + 1) {
            points = 1
        }
    }

    override fun onBackPressed() {
        binding.swFood.stopSound()
        super.onBackPressed()
    }
}