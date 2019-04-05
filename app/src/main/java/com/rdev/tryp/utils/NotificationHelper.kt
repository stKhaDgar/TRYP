package com.rdev.tryp.utils

import android.content.Context
import android.media.MediaPlayer
import android.os.Vibrator
import com.rdev.tryp.R
import io.realm.internal.SyncObjectServerFacade.getApplicationContext
import android.media.RingtoneManager

/**
 * Created by Andrey Berezhnoi on 05.04.2019.
 */


object NotificationHelper{

    fun approvedFromDriver(context: Context){
        val mp = MediaPlayer.create(context, R.raw.sound)
        mp.start()
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(1000)
    }

    fun statusChanged(context: Context){
        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(getApplicationContext(), notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(400)
    }

}