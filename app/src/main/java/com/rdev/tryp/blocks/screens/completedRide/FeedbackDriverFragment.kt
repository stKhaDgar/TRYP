package com.rdev.tryp.blocks.screens.completedRide

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdev.tryp.ContentActivity

import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.AvailableDriversChanged
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.firebaseDatabase.model.Feedback
import com.rdev.tryp.model.RealmUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_feedback_driver.view.*
import java.text.SimpleDateFormat
import java.util.*

class FeedbackDriverFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_feedback_driver, container, false)

        onClickListener(root)

        initUI(root)

        return root
    }

    private fun initUI(view: View){
        val database = (activity as? ContentActivity)?.database
        (activity as? ContentActivity)?.currentRide?.driver?.id?.let { id ->
            database?.getDriver(id, object : AvailableDriversChanged.GetData.Driver{
                override fun onCompleted(driver: Driver?) {
                    driver?.image?.let { url ->
                        Picasso.get().load(url).into(view.main_img)
                    }
                    driver?.firstName?.let { firstName ->
                        driver.lastName?.let { lastName ->
                            val tempName = "$firstName $lastName"
                            view.tv_name.text = tempName
                        }
                    }
                    driver?.rating?.let { rat ->
                        view.tv_rating.text = rat.toFloat().toString()
                    }
                }

            })
        }
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener {
            (activity as ContentActivity).goHomeOneTransition()
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                (activity as ContentActivity).goHomeOneTransition()
                true
            } else {
                false
            }
        }

        view.btnSubmit.setOnClickListener {
            (activity as? ContentActivity)?.let { act ->
                act.database?.let { db ->
                    act.currentRide.driver?.id?.let { id ->
                        val feedback = Feedback()
                        val user = RealmUtils(view.context, null).getCurrentUser()
                        feedback.clientId = user?.userId.toString()
                        feedback.rating = view.ratingBar.rating
                        feedback.message = view.etReview.text.toString()
                        feedback.created_at = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).format(Calendar.getInstance().time)
                        db.sendFeedback(id, feedback)
                    }
                }
            }
        }

        view.btnFavourite.setOnCheckedChangeListener { _, isChecked ->
            (activity as? ContentActivity)?.database?.addDriverToFavorite((activity as? ContentActivity)?.currentRide?.driver?.id, isChecked)
        }
    }

}