package com.rdev.tryp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PickerPhoneDialog {
    private String[] mTestArray;
    RecyclerView picker_rv;
    AlertDialog dialog1;

    public void showDialog(Context context, SelectCountryListener listener) {
        View v = LayoutInflater.from(context).inflate(R.layout.picker_dialog, null);
        dialog1 = new AlertDialog.Builder(context)
                .setView(v).create();

        picker_rv = v.findViewById(R.id.picker_rv);
        picker_rv.setLayoutManager(new LinearLayoutManager(context));
        mTestArray = context.getResources().getStringArray(R.array.CountryCodes);
        picker_rv.setAdapter(new PickerAdapter(mTestArray, listener));

        dialog1.show();

    }

    public void hideDialog() {
        dialog1.dismiss();
    }

}
