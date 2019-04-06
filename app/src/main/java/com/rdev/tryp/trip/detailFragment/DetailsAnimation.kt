package com.rdev.tryp.trip.detailFragment

import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView


class DetailsAnimation(private val view: View?, private val imageView: ImageView?, private val carView: View?) {

    companion object {
        private const val STATE_OPEN = "state_open"
        private const val STATE_CLOSE = "state_close"
    }

    private var state: String? = null

    init {
        state = STATE_CLOSE
    }

    fun start() {
        if (state === STATE_CLOSE) {
            open()
        } else {
            close()
        }
    }

    private fun open() {
        val imageAnimation = TranslateAnimation(0f, 0f, 0f, -200f)
        imageAnimation.duration = 1000
        imageAnimation.fillAfter = true
        imageView?.startAnimation(imageAnimation)

        val carImageAnimation = TranslateAnimation(0f, 0f, -10f, 40f)
        carImageAnimation.duration = 1000
        carImageAnimation.fillAfter = true
        carView?.startAnimation(carImageAnimation)

        val animation = TranslateAnimation(0f, 0f, 0f, -400f)
        animation.duration = 1000
        animation.fillAfter = true
        view?.startAnimation(animation)

        state = STATE_OPEN
    }

    private fun close() {
        val imageAnimation = TranslateAnimation(0f, 0f, -200f, 0f)
        imageAnimation.duration = 1000
        imageAnimation.fillAfter = true
        imageView?.startAnimation(imageAnimation)

        val carImageAnimation = TranslateAnimation(0f, 0f, 40f, -10f)
        carImageAnimation.duration = 1000
        carImageAnimation.fillAfter = true
        carView?.startAnimation(carImageAnimation)

        val animation = TranslateAnimation(0f, 0f, -400f, 0f)
        animation.duration = 1000
        animation.fillAfter = true
        view?.startAnimation(animation)

        state = STATE_CLOSE
    }

}