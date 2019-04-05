package com.rdev.tryp.blocks.forme

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.autocomplete.AutoCompleteAdapter
import com.rdev.tryp.blocks.forme.edit_addresses.AddressEditor
import com.rdev.tryp.blocks.forme.edit_addresses.Editor
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.model.TripPlace
import com.rdev.tryp.utils.PreferenceManager
import com.squareup.picasso.Picasso

import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Objects

import android.location.Location
import androidx.appcompat.widget.AppCompatEditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.net.*
import com.rdev.tryp.utils.LocationUpdatedListener

import com.rdev.tryp.utils.Utils.KEY_HOME
import com.rdev.tryp.utils.Utils.KEY_RECENT_FROM_1
import com.rdev.tryp.utils.Utils.KEY_RECENT_FROM_2
import com.rdev.tryp.utils.Utils.KEY_RECENT_TO_1
import com.rdev.tryp.utils.Utils.KEY_RECENT_TO_2
import com.rdev.tryp.utils.Utils.KEY_WORK
import com.rdev.tryp.utils.Utils.closeKeyboard
import com.rdev.tryp.utils.Utils.showKeyboard


@SuppressLint("ValidFragment")
class ProfileFragment @SuppressLint("ValidFragment")
constructor(private var startPos: TripPlace?, private var destination: TripPlace?) : Fragment(), AutoCompleteAdapter.onPlacePicked, View.OnClickListener, Editor.IView {

    private var geocoder: Geocoder? = null
    private var placesClient: PlacesClient? = null
    private var adapter: AutoCompleteAdapter? = null
    private var editLayout: RelativeLayout? = null

    private var adressTv: AppCompatEditText? = null
    private var adressTv2: AppCompatEditText? = null
    private var autoCompleteRv: RecyclerView? = null
    private var btnBack: ImageButton? = null
    private var btnEdit: ImageButton? = null
    private var mainEditText: AppCompatEditText? = null
    private var homeEditText: TextView? = null
    private var workEditText: TextView? = null
    private var cardView: CardView? = null
    private var recentFirst: RelativeLayout? = null
    private var recentSecond: RelativeLayout? = null
    private var homeAddress: RelativeLayout? = null
    private var workAddress: RelativeLayout? = null
    private var routeBtn: ImageView? = null
    private var editor: Editor.IEditor? = null
    private var mainPhoto: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initView(view)
        setSmallImage(view.context)
        initRecentRoutes()
        initAutoComplete()
        editor = AddressEditor()
        showFavoriteAddresses()

        adressTv2?.requestFocus()
        val currentAddress = (Objects.requireNonNull<FragmentActivity>(activity) as ContentActivity).currentAddress
        if (currentAddress != null) {
            adressTv?.setText(currentAddress)

            (activity as ContentActivity).currentLocation.onStartLocationUpdate(object : LocationUpdatedListener{
                override fun locationUpdated(location: Location) {
                    startPos?.coord = LatLng(location.latitude, location.longitude)
                    startPos?.locale = currentAddress
                }
            })

        }
        showKeyboard(Objects.requireNonNull<Context>(context))

        return view
    }

    private fun initView(view: View) {
        adressTv = view.findViewById(R.id.adress_tv)
        adressTv2 = view.findViewById(R.id.adress_tv_2)
        cardView = view.findViewById(R.id.top_card_view)
        autoCompleteRv = view.findViewById(R.id.autoCompleteRv)
        btnBack = view.findViewById(R.id.back_btn)
        recentFirst = view.findViewById(R.id.recent_relative_layout)
        recentSecond = view.findViewById(R.id.recent_relative_layout_2)
        homeAddress = view.findViewById(R.id.home_location_relative_layout)
        workAddress = view.findViewById(R.id.work_location_relative_layout)
        homeEditText = view.findViewById(R.id.home_tv)
        workEditText = view.findViewById(R.id.work_tv)
        btnEdit = view.findViewById(R.id.edit_btn)
        editLayout = view.findViewById(R.id.edit_layout)
        routeBtn = view.findViewById(R.id.route_btn)
        mainPhoto = view.findViewById(R.id.main_img)

        cardView?.setBackgroundResource(R.drawable.card_view_bg)
        autoCompleteRv?.layoutManager = LinearLayoutManager(context)
        btnBack?.setOnClickListener(this)
        recentSecond?.setOnClickListener(this)
        recentFirst?.setOnClickListener(this)
        homeAddress?.setOnClickListener(this)
        workAddress?.setOnClickListener(this)
        btnEdit?.setOnClickListener(this)
        routeBtn?.setOnClickListener(this)
        homeEditText?.text = PreferenceManager.getString(KEY_HOME)
        workEditText?.text = PreferenceManager.getString(KEY_WORK)
        adressTv?.setOnFocusChangeListener { _, _ ->
            resetAddressView(adressTv2)
            adapter?.setData(ArrayList())
            mainEditText = adressTv
            setCursorEnd(adressTv)
        }

        adressTv2?.setOnFocusChangeListener { _, _ ->
            resetAddressView(adressTv)
            adapter?.setData(ArrayList())
            mainEditText = adressTv2
            setCursorEnd(adressTv2)
        }
    }

    private fun setSmallImage(context: Context) {

        val img = RealmUtils(activity, null).getCurrentUser()?.image

        if (img != null && img != "null") {
            Picasso.get().load(img).centerCrop().resize(300, 300).into(mainPhoto)
        } else {
            val height = 100
            val width = 100
            val bitmapDraw = ContextCompat.getDrawable(context, R.drawable.small_person) as BitmapDrawable
            val b = bitmapDraw.bitmap
            val bitmap = Bitmap.createScaledBitmap(b, width, height, false)
            mainPhoto?.setImageBitmap(bitmap)
        }
    }

    private fun resetAddressView(textView: TextView?) {
        if (textView == adressTv) {
            if (startPos != null) {
                adressTv?.setText(startPos?.locale)
            } else {
                adressTv?.setText("")
            }
        }

        if (textView == adressTv2) {
            if (destination != null) {
                adressTv2?.setText(destination?.locale)
            } else {
                adressTv2?.setText("")
            }
        }
    }

    private fun showFavoriteAddresses() {
        val home = PreferenceManager.getTripPlace(KEY_HOME)
        val work = PreferenceManager.getTripPlace(KEY_WORK)

        if (home != null) {
            homeEditText?.text = home.locale
        } else {
            homeEditText?.text = "add home address"
        }

        if (work != null) {
            workEditText?.text = work.locale
        } else {
            workEditText?.text = "add work address"
        }
    }

    private fun initAutoComplete() {
        geocoder = Geocoder(context)

        if (startPos == null) {
            startPos = TripPlace()
        }
        if (destination == null) {
            destination = TripPlace()
        }

        adressTv?.setText(startPos?.locale)
        adressTv2?.setText(destination?.locale)

        adapter = AutoCompleteAdapter(ArrayList(), this@ProfileFragment)
        autoCompleteRv?.adapter = adapter

        //Autocomplete Realisation
        adressTv?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mainEditText = adressTv
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                context?.let { ctx -> placesClient = Places.createClient(ctx) }
                val request = FindAutocompletePredictionsRequest.builder()
                        .setQuery(charSequence.toString())
                        .build()
                placesClient?.findAutocompletePredictions(request)?.addOnCompleteListener { task -> task.result?.autocompletePredictions?.let { predictions -> adapter?.setData(predictions) } }
            }

            override fun afterTextChanged(s: Editable) {
                adapter?.setData(ArrayList())
            }
        })

        adressTv2?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mainEditText = adressTv2
                adapter?.setData(ArrayList())
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                context?.let { ctx -> placesClient = Places.createClient(ctx) }
                val request = FindAutocompletePredictionsRequest.builder()
                        .setQuery(charSequence.toString())
                        .build()
                placesClient?.findAutocompletePredictions(request)?.addOnCompleteListener { task -> adapter?.setData(task.result?.autocompletePredictions) }
            }

            override fun afterTextChanged(s: Editable) {
                adapter?.setData(ArrayList())
            }
        })

    }

    private fun initRecentRoutes() {
        val firstFrom = view?.findViewById(R.id.from_tv_1) as? TextView
        val firstTo = view?.findViewById(R.id.to_tv_1) as? TextView
        val secondFrom = view?.findViewById(R.id.from_tv_2) as? TextView
        val secondTo = view?.findViewById(R.id.to_tv_2) as? TextView
        var place: TripPlace? = PreferenceManager.getTripPlace(KEY_RECENT_FROM_1)

        if (place != null) {
            firstFrom?.text = place.locale
        } else {
            recentFirst?.visibility = View.INVISIBLE
            recentFirst?.setOnClickListener(null)
        }

        place = PreferenceManager.getTripPlace(KEY_RECENT_TO_1)
        if (place != null) {
            firstTo?.text = place.locale
        }


        place = PreferenceManager.getTripPlace(KEY_RECENT_FROM_2)
        if (place != null) {
            secondFrom?.text = place.locale
        } else {
            recentSecond?.visibility = View.INVISIBLE
            recentSecond?.setOnClickListener(null)
        }

        place = PreferenceManager.getTripPlace(KEY_RECENT_TO_2)
        if (place != null) {
            secondTo?.text = place.locale
        }
    }

    fun getLocationFromAddress(context: Context, strAddress: String): Address? {

        val coder = Geocoder(context)
        val address: List<Address>?
        var resultLocation: Address? = null

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }

            resultLocation = address[0]

        } catch (ex: IOException) {

            ex.printStackTrace()
        }

        return resultLocation
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> (Objects.requireNonNull<FragmentActivity>(activity) as ContentActivity).goHome()
            R.id.recent_relative_layout -> getRecentFirst()
            R.id.recent_relative_layout_2 -> getRecentSecond()
            R.id.home_location_relative_layout -> {
                homeEditText?.clearFocus()
                setTripPlace(PreferenceManager.getTripPlace(KEY_HOME))
            }
            R.id.work_location_relative_layout -> setTripPlace(PreferenceManager.getTripPlace(KEY_WORK))
            R.id.edit_btn -> activity?.let { act -> editor?.editAddresses(act, this) }
            R.id.route_btn -> setupTrip()
        }
    }

    private fun setupTrip() {
        if (isTripPlaceNotEmpty(startPos) && isTripPlaceNotEmpty(destination)) {
            onDestination(startPos, destination)
            saveRouteInRecent(startPos, destination)
        }
        closeKeyboard(Objects.requireNonNull<Context>(context))
    }

    private fun isTripPlaceNotEmpty(tripPlace: TripPlace?): Boolean {
        return if (tripPlace?.locale == null) {
            false
        } else tripPlace.coord != null
    }

    private fun setTripPlace(tripPlace: TripPlace?) {
        if (tripPlace == null) {
            return
        }

        if (mainEditText == null) {
            mainEditText = adressTv
        }

        if (mainEditText == adressTv2) {
            destination = tripPlace
            resetAddressView(adressTv2)
        }
        if (mainEditText == adressTv) {
            startPos = tripPlace
            resetAddressView(adressTv)
        }

        setCursorEnd(mainEditText)
    }

    override fun onPlace(prediction: AutocompletePrediction) {
        if (prediction.getPrimaryText(null).toString() == Objects.requireNonNull<Editable>(mainEditText?.text).toString()) {
            mainEditText?.setText(prediction.getFullText(null))
            adapter?.data?.clear()
            adapter?.notifyDataSetChanged()
        } else {
            mainEditText?.setText(prediction.getPrimaryText(null))
        }
        setCursorEnd(mainEditText)
        val placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME)
        val request = FetchPlaceRequest.newInstance(prediction.placeId, placeFields)
        placesClient?.fetchPlace(request)?.addOnCompleteListener { task ->
            if (mainEditText == adressTv2) {
                destination?.coord = Objects.requireNonNull<FetchPlaceResponse>(task.result).place.latLng
                destination?.locale = prediction.getFullText(null).toString()
            }
            if (mainEditText == adressTv) {
                (Objects.requireNonNull<FragmentActivity>(activity) as ContentActivity).currentAddress = null
                startPos?.coord = Objects.requireNonNull<FetchPlaceResponse>(task.result).place.latLng
                startPos?.locale = prediction.getFullText(null).toString()
            }
        }
    }

    private fun onDestination(start: TripPlace?, end: TripPlace?) {
        (activity as? ContentActivity)?.popBackStack()
        (activity as? ContentActivity)?.onDestinationPicked(start, end, true)
    }

    private fun saveRouteInRecent(start: TripPlace?, end: TripPlace?) {
        val secondFrom: TripPlace? = PreferenceManager.getTripPlace(KEY_RECENT_FROM_1)
        val secondTo: TripPlace? = PreferenceManager.getTripPlace(KEY_RECENT_TO_1)

        PreferenceManager.setTripPlace(KEY_RECENT_FROM_1, start)
        PreferenceManager.setTripPlace(KEY_RECENT_TO_1, end)

        PreferenceManager.setTripPlace(KEY_RECENT_FROM_2, secondFrom)
        PreferenceManager.setTripPlace(KEY_RECENT_TO_2, secondTo)
    }

    private fun getRecentFirst() {
        startPos = PreferenceManager.getTripPlace(KEY_RECENT_FROM_1)
        destination = PreferenceManager.getTripPlace(KEY_RECENT_TO_1)
        setupTrip()
    }

    private fun getRecentSecond() {
        startPos = PreferenceManager.getTripPlace(KEY_RECENT_FROM_2)
        destination = PreferenceManager.getTripPlace(KEY_RECENT_TO_2)
        setupTrip()
    }

    override fun showAddresses() {
        showFavoriteAddresses()
    }

    private fun setCursorEnd(editText: EditText?) {
        editText?.length()?.let { length ->
            val eText = editText.text
            Selection.setSelection(eText, length)
        }
    }

}