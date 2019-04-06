package com.rdev.tryp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PickerPhoneDialog {

    private lateinit var tvPicker: RecyclerView
    private lateinit var dialog1: AlertDialog

    fun showDialog(context: Context?, listener: SelectCountryListener) {
        val v = LayoutInflater.from(context).inflate(R.layout.picker_dialog, null)
        dialog1 = AlertDialog.Builder(context)
                .setView(v).create()

        tvPicker = v.findViewById(R.id.picker_rv)
        tvPicker.layoutManager = LinearLayoutManager(context)
        context?.resources?.getStringArray(R.array.CountryCodes)?.let { mTestArray ->
            tvPicker.adapter = PickerAdapter(mTestArray, listener)
        }

        dialog1.show()
    }

    fun hideDialog() {
        dialog1.dismiss()
    }

}
