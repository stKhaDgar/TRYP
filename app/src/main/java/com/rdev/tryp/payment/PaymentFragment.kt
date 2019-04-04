package com.rdev.tryp.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.helper.BaseFragment
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.payment.model.Card
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_payment.view.*

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 */

class PaymentFragment : BaseFragment() {
    private lateinit var arrayList: ArrayList<Card>
    private lateinit var adapter: PaymentCardsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        Realm.init(view.context)

        setDetails(view)
        onClickListener(view)

        return view
    }

    private fun setDetails(view: View){
        arrayList = RealmUtils(view.context,null).getCards()
        adapter = PaymentCardsAdapter(arrayList, activity as ContentActivity)

        if(arrayList.isNotEmpty()){
            view.layoutNoCards.visibility = View.GONE
            view.cardsLayout.visibility = View.VISIBLE

            view.rvCards.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            view.rvCards.adapter = adapter
        } else {
            view.layoutNoCards.visibility = View.VISIBLE
            view.cardsLayout.visibility = View.GONE
        }
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener { (activity as? ContentActivity)?.goHome() }
        view.btnAddNewCard.setOnClickListener { (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_PAYMENT_NEW_ENTRY) }
        view.tvAddNewCard.setOnClickListener { (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_PAYMENT_NEW_ENTRY) }
    }
}