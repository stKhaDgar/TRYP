package com.rdev.tryp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class SetLocationFragment : Fragment() {
    private lateinit var btnLocation: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.twenty_three, container, false)
        btnLocation = v.findViewById(R.id.location_btn)
        btnLocation.setOnClickListener { (activity as ContentActivity).zoomToCurrentLocation() }
        return v
    }

}