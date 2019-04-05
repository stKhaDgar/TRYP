package com.rdev.tryp.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

import com.rdev.tryp.PickerPhoneDialog
import com.rdev.tryp.R
import com.rdev.tryp.WelcomeActivity
import com.rdev.tryp.intro.createAccount.CreateActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

import com.rdev.tryp.utils.Utils.getCountryCode
import com.rdev.tryp.utils.Utils.getCountryName
import com.rdev.tryp.utils.Utils.getDialingCode

class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var backBtn: ImageButton
    private lateinit var tvSignUp: TextView
    private lateinit var btnSendCode: CardView
    private lateinit var etPhoneNumber: EditText
    private lateinit var phoneCard: CardView
    private lateinit var tvCountry: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.login_fragment, container, false)
        backBtn = v.findViewById(R.id.back_btn)
        backBtn.setOnClickListener(this)
        tvCountry = v.findViewById(R.id.country_tv)
        tvSignUp = v.findViewById(R.id.sign_up_tv)
        tvSignUp.setOnClickListener(this)
        btnSendCode = v.findViewById(R.id.send_code_card)
        btnSendCode.setOnClickListener(this)
        etPhoneNumber = v.findViewById(R.id.phone_number_et)
        phoneCard = v.findViewById(R.id.cardView2)
        phoneCard.setOnClickListener(this)
        return v
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back_btn -> {
                startActivity(Intent(context, WelcomeActivity::class.java))
                activity?.finish()
            }
            R.id.sign_up_tv -> {
                startActivity(Intent(context, CreateActivity::class.java))
                activity?.finish()
            }
            R.id.send_code_card -> {
                (activity as LoginActivity).number.phone_number = etPhoneNumber.text.toString()
                (activity as LoginActivity).onSendCode()
            }
            R.id.cardView2 -> {
                val dialog = PickerPhoneDialog()
                dialog.showDialog(context) { data ->
                    dialog.hideDialog()
                    val number = (activity as LoginActivity).number
                    number.dialing_code = getDialingCode(data)
                    number.country_code = getCountryCode(data)
                    val phone = getCountryName(data) + " (+" + number.dialing_code + ")"
                    tvCountry.text = phone
                }
            }
        }
    }

}