package com.rdev.tryp.blocks.connect.connect_cancel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R

import java.util.Objects
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class ConnectCancelFragment : Fragment(), View.OnClickListener {

    private var root:           View? = null
    private var backBtn:        ImageView? = null
    private var confirmBtn:     ImageView? = null
    private var radioGroup:     RadioGroup? = null
    private var radioBtn1:      RadioButton? = null
    private var radioBtn2:      RadioButton? = null
    private var radioBtn3:      RadioButton? = null

    private val currentChecked = when {
        radioBtn1?.isChecked == true    -> radioBtn1?.text.toString()
        radioBtn2?.isChecked == true    -> radioBtn2?.text.toString()
        radioBtn3?.isChecked == true    -> radioBtn3?.text.toString()
        else -> null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_connect_cancel, container, false)

        initView()

        return root
    }

    private fun initView() {
        backBtn    =   root?.findViewById(R.id.back_btn)
        confirmBtn =   root?.findViewById(R.id.confirm_button)
        radioGroup =   root?.findViewById(R.id.radio_group)
        radioBtn1  =   root?.findViewById(R.id.radio_btn_1)
        radioBtn2  =   root?.findViewById(R.id.radio_btn_2)
        radioBtn3  =   root?.findViewById(R.id.radio_btn_3)

        backBtn?.setOnClickListener(this)
        confirmBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> (Objects.requireNonNull<FragmentActivity>(activity) as ContentActivity).popBackStack()
            R.id.confirm_button -> {
                val idTemp = (activity as ContentActivity).currentRide.id
                (activity as ContentActivity).database.cancelRide(idTemp, currentChecked)
                (Objects.requireNonNull<FragmentActivity>(activity) as ContentActivity).clearBackStack()
                (activity as ContentActivity).clearMap()
                (activity as ContentActivity).initMap()
            }
        }
    }

    private fun getCheckedReason(id: Int): String {
        when (id) {
            R.id.radio_btn_1 -> return radioBtn1?.text.toString()
            R.id.radio_btn_2 -> return radioBtn2?.text.toString()
            R.id.radio_btn_3 -> return radioBtn3?.text.toString()
        }
        return radioBtn1?.text.toString()
    }

}
