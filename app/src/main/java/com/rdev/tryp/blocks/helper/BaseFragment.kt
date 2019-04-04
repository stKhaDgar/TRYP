package com.rdev.tryp.blocks.helper

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.blocks.favourite_drivers.FavouriteDriversFragment
import com.rdev.tryp.blocks.reward_profile.RewardProfileFragment
import com.rdev.tryp.blocks.screens.help.HelpFragment
import com.rdev.tryp.payment.PaymentFragment

/**
 * Created by Andrey Berezhnoi on 04.04.2019.
 */


abstract class BaseFragment: Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                (activity as ContentActivity).openMap()


//                val navigation = (activity as ContentActivity).navigationView
//
//                var i = 0
//                while (i < navigation.menu.size()){
//
//                    if(navigation.menu.getItem(i).isChecked){
//                        when{
//                            navigation.menu.getItem(i).title == "Favorite" && this.javaClass.simpleName ==
//                                    FavouriteDriversFragment::class.java.simpleName -> { (activity as ContentActivity).goHome() }
//                            navigation.menu.getItem(i).title == "Payment" && this.javaClass.simpleName ==
//                                    PaymentFragment::class.java.simpleName -> { (activity as ContentActivity).goHome() }
//                            navigation.menu.getItem(i).title == "Rewards" && this.javaClass.simpleName ==
//                                    RewardProfileFragment::class.java.simpleName -> { (activity as ContentActivity).goHome() }
//                            navigation.menu.getItem(i).title == "Help" && this.javaClass.simpleName ==
//                                    HelpFragment::class.java.simpleName -> { (activity as ContentActivity).goHome() }
//                        }
//                    }
//
//                    i++
//                }

                true
            } else {
                false
            }
        }
    }

}