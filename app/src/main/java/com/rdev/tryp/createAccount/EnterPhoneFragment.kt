package com.rdev.tryp.createAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import com.rdev.tryp.PickerPhoneDialog
import com.rdev.tryp.R
import com.rdev.tryp.intro.createAccount.CreateActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.rdev.tryp.SelectCountryListener

import com.rdev.tryp.utils.Utils.getCountryCode
import com.rdev.tryp.utils.Utils.getCountryName
import com.rdev.tryp.utils.Utils.getDialingCode

class EnterPhoneFragment : Fragment(), View.OnClickListener {
    private lateinit var btnBack: ImageButton
    private lateinit var btnCreateAccount: CardView
    private lateinit var etPhone: EditText
    private lateinit var phonePickerCardView: CardView
    private lateinit var tvCountryPhone: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.enter_phone, container, false)

        btnCreateAccount = v.findViewById(R.id.create_account_card)
        btnCreateAccount.setOnClickListener(this)
        btnBack = v.findViewById(R.id.back_btn)
        btnBack.setOnClickListener(this)
        etPhone = v.findViewById(R.id.phone_et)
        phonePickerCardView = v.findViewById(R.id.cardView2)
        phonePickerCardView.setOnClickListener(this)
        tvCountryPhone = v.findViewById(R.id.country_phone_tv)

        return v
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back_btn -> (activity as CreateActivity).onFinish()
            R.id.create_account_card -> if (etPhone.text.toString().isEmpty()) {
                Toast.makeText(context, "Please enter your phone number", Toast.LENGTH_SHORT).show()
            } else {
                (activity as? CreateActivity)?.createUser?.phone_number = etPhone.text.toString()
                (activity as? CreateActivity)?.createAccount()
            }
            R.id.cardView2 -> {
                val dialog = PickerPhoneDialog()
                dialog.showDialog(context, object : SelectCountryListener{
                    override fun onSelect(data: String) {
                        dialog.hideDialog()
                        val number = (activity as? CreateActivity)?.createUser
                        number?.dialing_code = getDialingCode(data)
                        number?.country_code = getCountryCode(data)
                        tvCountryPhone.text = "${getCountryName(data)} (+${number?.dialing_code})"
                    }
                })
            }
        }
    }

}