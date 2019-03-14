package com.rdev.tryp.blocks.forme.edit_addresses;

import android.app.Activity;

public interface Editor {
    interface IEditor {
        void editAddresses(Activity activity, IView view);
    }

    interface IView {
        void showAddresses();
    }
}
