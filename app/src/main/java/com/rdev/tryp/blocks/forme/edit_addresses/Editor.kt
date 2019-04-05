package com.rdev.tryp.blocks.forme.edit_addresses

import android.app.Activity

interface Editor {

    interface IEditor {
        fun editAddresses(activity: Activity, view: IView)
    }

    interface IView {
        fun showAddresses()
    }

}
