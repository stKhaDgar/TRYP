package com.rdev.tryp.blocks.forme.edit_addresses

import android.app.Activity
import com.rdev.tryp.R

import androidx.appcompat.app.AppCompatActivity


class AddressEditor : Editor.IEditor {

    override fun editAddresses(activity: Activity, view: Editor.IView) {
        val fragment = EditAddressesFragment(view)
        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .add(R.id.screenContainer, fragment)
                .addToBackStack("edit_address")
                .commit()
    }

}