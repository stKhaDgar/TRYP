package com.rdev.tryp.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.model.RealmCallback
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.payment.model.Card
import kotlinx.android.synthetic.main.fragment_add_card.view.*
import kotlin.random.Random
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage


/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */

class AddCardFragment : Fragment() {
    private var currentTypeCard = ""
    private var editCard: Card? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_card, container, false)

        onClickListener(view)
        initCardForm(view)

        (activity as ContentActivity).b?.let { bundle ->
            val item = bundle.getSerializable(ContentActivity.IS_EDIT_CARD)
            (item as? Card).let { card ->
                editCard = card
                view.title.text = getString(R.string.edit_card_text)
                view.btnAddNewCard.text = getString(R.string.change_card_text)
                view.btnDeleteCard.visibility = View.VISIBLE
                view.cardForm.cardEditText.setText(card?.number)
                view.cardForm.cvvEditText.setText(card?.cvv)
                val date = "${card?.expirationMonth}${card?.expirationYear}"
                view.cardForm.expirationDateEditText.setText(date)
            }
        }

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
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener { (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_PAYMENT) }
        view.btnCamera.setOnClickListener {
            view.cardForm.scanCard(activity as ContentActivity)
        }
        view.btnAddNewCard.setOnClickListener {
            if(!view.cardForm.isValid){
                view.cardForm.validate()
            } else {
                val card = Card()

                card.id = if(editCard != null) editCard?.id else Random.nextInt(99999).toString()
                card.number = view.cardForm.cardNumber
                card.type = currentTypeCard
                card.expirationMonth = view.cardForm.expirationMonth
                card.expirationYear = view.cardForm.expirationYear
                card.cvv = view.cardForm.cvv

                RealmUtils(object : RealmCallback{
                    override fun dataUpdated() {
                        (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_PAYMENT)
                    }

                    override fun error() {
                        Toast.makeText(view.context, "Something wrong", Toast.LENGTH_LONG).show()
                    }

                }).pushCard(card)
            }
        }
        view.btnDeleteCard.setOnClickListener {
            RealmUtils(object : RealmCallback{
                override fun dataUpdated() {
                    (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_PAYMENT)
                }

                override fun error() {
                    Toast.makeText(view.context, "Something wrong", Toast.LENGTH_LONG).show()
                }

            }).deleteCard(editCard?.id)
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as ContentActivity).b = null
    }
}