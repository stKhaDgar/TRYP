package com.rdev.tryp.blocks.forme.edit_addresses;

import android.app.Activity;
import com.rdev.tryp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AddressEditor implements Editor.IEditor {

    @Override
    public void editAddresses(Activity activity, Editor.IView view) {
        Fragment fragment = new EditAddressesFragment(view);
        ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction()
                .add(R.id.screenContainer, fragment)
                .addToBackStack("edit_address")
                .commit();
    }

}
