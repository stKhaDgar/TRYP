package com.rdev.tryp.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton

import com.rdev.tryp.PinEntryEditText
import com.rdev.tryp.R
import androidx.fragment.app.Fragment

class ConfirmLoginFragment : Fragment(), View.OnClickListener {

    private lateinit var backBtn: ImageButton
    private lateinit var verifySmsBtn: ImageButton
    private lateinit var pinEntryEditText: PinEntryEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.confirm_layout, container, false)

        backBtn = v.findViewById(R.id.back_btn)
        backBtn.setOnClickListener(this)
        pinEntryEditText = v.findViewById(R.id.pin_edit_text)
        verifySmsBtn = v.findViewById(R.id.send_btn)
        verifySmsBtn.setOnClickListener(this)

        pinEntryEditText.requestFocus()
        showKeyboard()

        return v
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back_btn -> (activity as? LoginActivity)?.goBack()
            R.id.send_btn -> (activity as? LoginActivity)?.verifySms(pinEntryEditText.text?.toString())
        }
        closeKeyboard()
    }

    private fun showKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun closeKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}
