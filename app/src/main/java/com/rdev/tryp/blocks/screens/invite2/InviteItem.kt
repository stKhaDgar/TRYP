package com.rdev.tryp.blocks.screens.invite2

import androidx.annotation.DrawableRes

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */


class InviteItem(@field:DrawableRes val icon: Int, val name: String, val number: String, val status: Status) {
    enum class Status {
        NotInvited, Invited, Followed
    }
}
