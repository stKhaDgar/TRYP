package com.rdev.tryp.blocks.forme.edit_addresses

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.rdev.tryp.R
import com.rdev.tryp.autocomplete.AutoCompleteAdapter
import com.rdev.tryp.model.TripPlace
import com.rdev.tryp.utils.PreferenceManager
import com.rdev.tryp.utils.Utils

import java.util.ArrayList
import java.util.Arrays

import androidx.appcompat.widget.AppCompatEditText
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.rdev.tryp.utils.Utils.KEY_HOME
import com.rdev.tryp.utils.Utils.KEY_WORK


@SuppressLint("ValidFragment")
class EditAddressesFragment @SuppressLint("ValidFragment")
constructor(private val addressView: Editor.IView) : Fragment(), AutoCompleteAdapter.onPlacePicked, View.OnClickListener {

    private var home: TripPlace? = null
    private var work: TripPlace? = null
    private var homeEt: AppCompatEditText? = null
    private var workEt: AppCompatEditText? = null
    private var mainEt: AppCompatEditText? = null
    private var completeRv: RecyclerView? = null
    private var confirmEditBtn: CardView? = null
    private var adapter: AutoCompleteAdapter? = null
    private var geocoder: Geocoder? = null
    private var placesClient: PlacesClient? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_addresses_edit, container, false)

        initView()
        initAutoComplete()

        return view
    }

    private fun initView() {
        homeEt = view?.findViewById(R.id.home_edit_text)
        workEt = view?.findViewById(R.id.work_edit_text)
        completeRv = view?.findViewById(R.id.complete_recycler_view)
        confirmEditBtn = view?.findViewById(R.id.confirm_edit_btn)
        confirmEditBtn?.setOnClickListener(this)

        completeRv?.layoutManager = LinearLayoutManager(context)

        homeEt?.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            adapter?.setData(ArrayList())
            mainEt = homeEt
        }

        workEt?.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            adapter?.setData(ArrayList())
            mainEt = workEt
        }
    }

    private fun initAutoComplete() {
        geocoder = Geocoder(context)
        home = PreferenceManager.getTripPlace(KEY_HOME)
        work = PreferenceManager.getTripPlace(KEY_WORK)

        if (home == null) {
            home = TripPlace()
        }

        if (work == null) {
            work = TripPlace()
        }

        homeEt?.setText(home?.locale)
        workEt?.setText(work?.locale)

        adapter = AutoCompleteAdapter(ArrayList(), this)
        completeRv?.adapter = adapter

        //Autocomplete Realisation
        homeEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mainEt = homeEt
                adapter?.setData(ArrayList())
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                context?.let { ctx -> placesClient = Places.createClient(ctx) }

                val request = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setQuery(charSequence.toString())
                        .build()
                placesClient?.findAutocompletePredictions(request)?.addOnCompleteListener { task -> adapter?.setData(task.result?.autocompletePredictions) }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        workEt?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mainEt = workEt
                adapter?.setData(ArrayList())
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                context?.let { ctx -> placesClient = Places.createClient(ctx) }

                val request = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setQuery(charSequence.toString())
                        .build()
                placesClient?.findAutocompletePredictions(request)?.addOnCompleteListener { task -> adapter?.setData(task.result?.autocompletePredictions) }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.confirm_edit_btn -> {
                AddressNetworkService.saveAddresses(home, work)
                addressView.showAddresses()
                activity?.supportFragmentManager?.beginTransaction()
                        ?.remove(this)
                        ?.commit()
            }
        }
    }

    override fun onPlace(prediction: AutocompletePrediction) {
        mainEt?.setText(prediction.getPrimaryText(null))
        val placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME)
        val request = FetchPlaceRequest.newInstance(prediction.placeId, placeFields)
        placesClient?.fetchPlace(request)?.addOnCompleteListener { task ->
            if (mainEt == workEt) {
                work?.coord = task.result?.place?.latLng
                work?.locale = prediction.getFullText(null).toString()
                Utils.closeKeyboard(context)
                adapter?.setData(ArrayList())
            }
            if (mainEt == homeEt) {
                home?.coord = task.result?.place?.latLng
                home?.locale = prediction.getFullText(null).toString()
                Utils.closeKeyboard(context)
                adapter?.setData(ArrayList())
            }
        }
    }

}