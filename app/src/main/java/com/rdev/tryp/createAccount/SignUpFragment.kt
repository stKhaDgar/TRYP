package com.rdev.tryp.createAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton

import com.rdev.tryp.R
import com.rdev.tryp.intro.createAccount.CreateActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var btnBack: ImageButton
    private lateinit var signUpCard: CardView
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etInvitedBy: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.sign_up_fragment, container, false)

        btnBack = v.findViewById(R.id.back_btn)
        btnBack.setOnClickListener(this)
        signUpCard = v.findViewById(R.id.sign_up_card)
        signUpCard.setOnClickListener(this)
        etFirstName = v.findViewById(R.id.sign_up_first_name_et)
        etLastName = v.findViewById(R.id.sign_up_last_name_et)
        etEmail = v.findViewById(R.id.sign_up_email_et)
        etInvitedBy = v.findViewById(R.id.sign_up_reffered_et)

        return v
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back_btn       -> (activity as CreateActivity).goBack()
            R.id.sign_up_card   -> {
                (activity as? CreateActivity)?.createUser?.last_name    =   etLastName.text.toString()
                (activity as? CreateActivity)?.createUser?.first_name   =   etFirstName.text.toString()
                (activity as? CreateActivity)?.createUser?.email        =   etEmail.text.toString()
                (activity as? CreateActivity)?.createUser?.ref_code     =   etInvitedBy.text.toString()
                (activity as? CreateActivity)?.signUp()
            }
        }
    }

}