package com.rdev.tryp.blocks.screens.recap;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.rdev.tryp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */
public class RecapFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recap, container, false);

        View gradient = view.findViewById(R.id.recap_gradient);
        gradient.setBackground(generateGradient(R.array.recap_gradient));

        initChart(view);

        return view;
    }

    private void initChart(View view) {
        LineChart chart = view.findViewById(R.id.recap_chart);
        Random random = new Random();

        List<Entry> friendsEntries = new ArrayList<>();
        List<Entry> linksEntries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            friendsEntries.add(new Entry(i, random.nextInt(10), "Test"));
            linksEntries.add(new Entry(i, random.nextInt(10), "Test"));
        }

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");

        LineDataSet setFriends = new LineDataSet(friendsEntries, "Friends Trips");
        setFriends.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        setFriends.setDrawCircles(false);
        setFriends.setHighlightEnabled(true);
        setFriends.setLineWidth(2f);
        setFriends.setColor(Color.parseColor("#0172FF"));
        setFriends.setDrawFilled(true);
        setFriends.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.gradient_chart_friends));
        setFriends.setDrawValues(false);

        LineDataSet setLinks = new LineDataSet(linksEntries, "Link Shared");
        setLinks.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        setLinks.setDrawCircles(false);
        setLinks.setHighlightEnabled(false);
        setLinks.setLineWidth(2f);
        setLinks.setColor(Color.parseColor("#FF0000"));
        setLinks.setDrawFilled(true);
        setLinks.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.gradient_chart_links));
        setLinks.setDrawValues(false);

        LineData lineData = new LineData(setFriends, setLinks);
        chart.setData(lineData);
        chart.setTouchEnabled(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setTextColor(ContextCompat.getColor(getContext(), R.color.text_default));
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisRight().setAxisMinimum(0);
        chart.getAxisLeft().setSpaceTop(35);
        chart.getAxisRight().setSpaceTop(35);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawBorders(true);
        chart.setBorderColor(Color.parseColor("#0D979797"));
        chart.setBorderWidth(1);
        chart.getXAxis().setValueFormatter((value, axis) ->  xAxisLabel.get((int) value));

        chart.invalidate();
    }

    private Drawable generateGradient(@ArrayRes int gradient) {
        int[] colors = getResources().getIntArray(gradient);
        GradientDrawable colorDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        colorDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        colorDrawable.setShape(GradientDrawable.RECTANGLE);

        return colorDrawable;
    }
}
