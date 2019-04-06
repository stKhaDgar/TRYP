package com.rdev.tryp.navigation

import android.content.Intent
import android.os.Bundle

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.intro.IntroActivity
import com.rdev.tryp.trip.tryp_later.TrypLaterActivity

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NavigationActivity : AppCompatActivity(), NavigationAdapter.NavListener {

    private lateinit var rvNavigation: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        rvNavigation = findViewById(R.id.navigation_rv)
        rvNavigation.adapter = NavigationAdapter(this)
        rvNavigation.layoutManager = LinearLayoutManager(this)
    }

    override fun onNavigationClick(pos: Int) {
        var intent: Intent? = null
        when (pos) {
            0 ->    intent = Intent(this, IntroActivity::class.java)
            1 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "tryp")
            }
            2 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "detail")
            }
            3 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "f")
            }
            4 ->    intent = Intent(this, TrypLaterActivity::class.java)
            5 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "car")
            }
            6 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "confirm")
            }
            7 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "set")
            }
            8 -> {
                    intent = Intent(this, ContentActivity::class.java)
                    intent.putExtra("tag", "connect")
            }
        }
        startActivity(intent)
    }

}