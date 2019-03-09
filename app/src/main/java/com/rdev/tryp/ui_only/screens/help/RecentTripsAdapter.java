package com.rdev.tryp.ui_only.screens.help;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdev.tryp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.inflationx.calligraphy3.CalligraphyTypefaceSpan;
import io.github.inflationx.calligraphy3.TypefaceUtils;

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */
public class RecentTripsAdapter extends RecyclerView.Adapter<RecentTripsAdapter.ViewHolder> {

    private Context context;
    private List<RecentTripItem> items;

    public RecentTripsAdapter(Context context, List<RecentTripItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentTripItem item = items.get(position);

        holder.userIcon.setImageResource(item.getUserIcon() == 0 ? R.drawable.invite_default_icon : item.getUserIcon());
        holder.isActive.setVisibility(item.isActive() ? View.VISIBLE : View.INVISIBLE);
        setDate(holder.time, item.getDate());
        holder.message.setText(item.getMessage());
        holder.amount.setText(item.getAmount());
        holder.distance.setText(item.getDistance());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private void setDate(TextView dateView, String date) {
        int index = date.indexOf(", ");
        if (index == -1) {
            dateView.setText(date);
        }

        String boldPart = date.substring(0, index);
        int coloredPart1Index = date.indexOf(boldPart);

        Spannable spannable = new SpannableString(date);
        if (coloredPart1Index != -1 && context != null) {
            Typeface typeface = TypefaceUtils.load(context.getAssets(), "fonts/OpenSans-Bold.ttf");

            spannable.setSpan(new CalligraphyTypefaceSpan(typeface),
                    coloredPart1Index,
                    coloredPart1Index + boldPart.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        dateView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userIcon;
        View isActive;
        TextView time;
        TextView message;
        TextView amount;
        TextView distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userIcon = itemView.findViewById(R.id.recent_user_icon);
            isActive = itemView.findViewById(R.id.recent_active);
            time = itemView.findViewById(R.id.recent_date);
            message = itemView.findViewById(R.id.recent_message);
            amount = itemView.findViewById(R.id.recent_amount);
            distance = itemView.findViewById(R.id.recent_distance);
        }
    }
}
