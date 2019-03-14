package com.rdev.tryp.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdev.tryp.ContentActivity

import com.rdev.tryp.R
import kotlinx.android.synthetic.main.fragment_add_card.view.*

class AddCardFragment : Fragment() {

    private var currentTypeCard = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_card, container, false)

        onClickListener(view)
        initCardForm(view)

        return view
    }

    private fun initCardForm(view: View){
        view.cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .actionLabel("Purchase")
                .setup(activity as ContentActivity)

        view.cardForm.setOnCardTypeChangedListener {
            currentTypeCard = it.name
        }

        if(view.cardForm.isCardScanningAvailable) { view.btnCamera.visibility = View.VISIBLE }

        view.btnCamera.setOnClickListener { view.cardForm.scanCard(activity as ContentActivity) }
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener { (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_PAYMENT) }
        view.btnAddNewCard.setOnClickListener {
            if(!view.cardForm.isValid){
                view.cardForm.validate()
            } else {

            }
        }
    }
}