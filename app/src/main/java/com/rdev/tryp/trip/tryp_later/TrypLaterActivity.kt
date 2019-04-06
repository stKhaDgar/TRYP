package com.rdev.tryp.trip.tryp_later

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View

import com.rdev.tryp.R

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class TrypLaterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dailyCard: CardView
    private lateinit var timeCard: CardView
    private lateinit var weekCard: CardView
    private lateinit var monthCard: CardView
    private lateinit var checkedCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tryp_later)
        dailyCard = findViewById(R.id.materialCardView8)
        timeCard = findViewById(R.id.materialCardView9)
        weekCard = findViewById(R.id.materialCardView10)
        monthCard = findViewById(R.id.materialCardView11)

        dailyCard.setOnClickListener(this)
        timeCard.setOnClickListener(this)
        weekCard.setOnClickListener(this)
        monthCard.setOnClickListener(this)
        checkedCard = dailyCard
    }

    override fun onClick(view: View) {}

}