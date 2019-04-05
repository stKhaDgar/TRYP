package com.rdev.tryp.blocks.screens.recap

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.rdev.tryp.R

import java.util.ArrayList
import java.util.Random

import androidx.annotation.ArrayRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */


class RecapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recap, container, false)

        val gradient = view.findViewById<View>(R.id.recap_gradient)
        gradient.background = generateGradient(R.array.recap_gradient)

        initChart(view)

        return view
    }

    private fun initChart(view: View) {
        val chart = view.findViewById<LineChart>(R.id.recap_chart)
        val random = Random()

        val friendsEntries = ArrayList<Entry>()
        val linksEntries = ArrayList<Entry>()
        for (i in 0..6) {
            friendsEntries.add(Entry(i.toFloat(), random.nextInt(10).toFloat(), "Test"))
            linksEntries.add(Entry(i.toFloat(), random.nextInt(10).toFloat(), "Test"))
        }

        val xAxisLabel = ArrayList<String>()
        xAxisLabel.add("Mon")
        xAxisLabel.add("Tue")
        xAxisLabel.add("Wed")
        xAxisLabel.add("Thu")
        xAxisLabel.add("Fri")
        xAxisLabel.add("Sat")
        xAxisLabel.add("Sun")

        val setFriends = LineDataSet(friendsEntries, "Friends Trips")
        setFriends.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        setFriends.setDrawCircles(false)
        setFriends.isHighlightEnabled = true
        setFriends.lineWidth = 2f
        setFriends.color = Color.parseColor("#0172FF")
        setFriends.setDrawFilled(true)
        setFriends.fillDrawable = ContextCompat.getDrawable(view.context, R.drawable.gradient_chart_friends)
        setFriends.setDrawValues(false)

        val setLinks = LineDataSet(linksEntries, "Link Shared")
        setLinks.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        setLinks.setDrawCircles(false)
        setLinks.isHighlightEnabled = false
        setLinks.lineWidth = 2f
        setLinks.color = Color.parseColor("#FF0000")
        setLinks.setDrawFilled(true)
        setLinks.fillDrawable = ContextCompat.getDrawable(view.context, R.drawable.gradient_chart_links)
        setLinks.setDrawValues(false)

        val lineData = LineData(setFriends, setLinks)
        chart.data = lineData
        chart.setTouchEnabled(false)
        chart.xAxis.isEnabled = true
        chart.xAxis.setDrawAxisLine(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.textColor = ContextCompat.getColor(view.context, R.color.text_default)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.axisLeft.setDrawLabels(false)
        chart.axisRight.setDrawLabels(false)
        chart.axisLeft.setDrawAxisLine(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisLeft.axisMinimum = 0f
        chart.axisRight.axisMinimum = 0f
        chart.axisLeft.spaceTop = 35f
        chart.axisRight.spaceTop = 35f
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setDrawBorders(true)
        chart.setBorderColor(Color.parseColor("#0D979797"))
        chart.setBorderWidth(1f)
        chart.xAxis.setValueFormatter { value, _ -> xAxisLabel[value.toInt()] }

        chart.invalidate()
    }

    private fun generateGradient(@ArrayRes gradient: Int): Drawable {
        val colors = resources.getIntArray(gradient)
        val colorDrawable = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        colorDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        colorDrawable.shape = GradientDrawable.RECTANGLE

        return colorDrawable
    }

}
