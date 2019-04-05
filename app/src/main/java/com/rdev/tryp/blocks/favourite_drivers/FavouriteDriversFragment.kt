package com.rdev.tryp.blocks.favourite_drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.helper.BaseFragment
import com.rdev.tryp.firebaseDatabase.utils.DatabaseUtils
import java.util.ArrayList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdev.tryp.firebaseDatabase.AvailableDriversChanged
import com.rdev.tryp.firebaseDatabase.model.Driver

class FavouriteDriversFragment : BaseFragment(), View.OnClickListener {

    private var root: View? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: Adapter? = null
    private var confirmLayout: RelativeLayout? = null
    private var position: Int = 0
    private var backBtn: ImageView? = null
    private val drivers: MutableList<FavoriteDriver> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_favourite_drivers, container, false)

        initView()

        return root
    }

    inner class OnItemClickListener {
        internal fun onItemClick(view: View, pos: Int) {
            position = pos
            when (view.id) {
                R.id.like_layout -> {
                    adapter?.disLike(position)
                    showConfirm()
                }
            }
        }
    }

    private fun initView() {
        backBtn = root?.findViewById(R.id.back_btn)
        backBtn?.setOnClickListener(this)

        recyclerView = root?.findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.isNestedScrollingEnabled = false

        (activity as ContentActivity).database.getFavoritedDrivers(object : AvailableDriversChanged.GetData.Driver{
            override fun onCompleted(driver: Driver?) {
                if (!DatabaseUtils.arrayContainsDriverId(drivers, driver?.driverId)) {
                    val temp = FavoriteDriver(driver?.firstName, driver?.lastName)
                    temp.id = driver?.driverId
                    temp.category = driver?.category
                    temp.photo = driver?.image

                    drivers.add(temp)
                    adapter?.notifyItemInserted(drivers.size - 1)
                }
            }
        })

        val listener = OnItemClickListener()
        adapter = Adapter(drivers, context, listener)

        recyclerView?.adapter = adapter
        adapter?.notifyDataSetChanged()

        val confirmButton = root?.findViewById<View>(R.id.confirm_button)
        confirmButton?.setOnClickListener(this)

        confirmLayout = root?.findViewById(R.id.confirm_layout)
        confirmLayout?.setOnClickListener(this)
        hideConfirm()
    }

    private fun showConfirm() {
        val btnTemp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        confirmLayout?.layoutParams = btnTemp
    }

    private fun hideConfirm() {
        val btnTemp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0)
        confirmLayout?.layoutParams = btnTemp
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.confirm_button -> onPositiveClick()
            R.id.confirm_layout -> onNegativeClick()
            R.id.back_btn -> (activity as ContentActivity).goHome()
        }
    }

    private fun onPositiveClick() {
        (activity as ContentActivity).database.addDriverToFavorite(drivers[position].id, false)
        adapter?.delete(position)
        hideConfirm()
    }

    private fun onNegativeClick() {
        adapter?.like(position)
        hideConfirm()
    }

}