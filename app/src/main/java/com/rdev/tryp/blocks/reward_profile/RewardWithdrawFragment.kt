package com.rdev.tryp.blocks.reward_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import kotlinx.android.synthetic.main.fragment_reward_withdraw.view.*

class RewardWithdrawFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reward_withdraw, container, false)
        view.confirmedText.text = view.context.getText(R.string.rewards_withdraw_confirmed_text)
        view.back_btn.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back_btn -> { (activity as? ContentActivity)?.startFragment(ContentActivity.TYPE_REWARD_POINTS) }
        }
    }
}
