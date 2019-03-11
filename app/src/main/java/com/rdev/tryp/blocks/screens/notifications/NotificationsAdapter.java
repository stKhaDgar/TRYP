package com.rdev.tryp.blocks.screens.notifications;

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
 * Created by Alexey Matrosov on 03.03.2019.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_PROMO = 1;

    private List<NotificationItem> items;
    private Context context;

    public NotificationsAdapter(Context context, List<NotificationItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        NotificationItem item = items.get(position);
        switch (item.getType()) {
            case Promo: return TYPE_PROMO;
            case Normal: return  TYPE_TEXT;
            default: throw new IllegalStateException("Unknown item type");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TEXT: {
                View view = LayoutInflater.from(context).inflate(R.layout.item_notificatio_text, parent, false);
                return new TextViewHolder(view);
            }

            case TYPE_PROMO: {
                View view = LayoutInflater.from(context).inflate(R.layout.item_notification_promo, parent, false);
                return new PromoViewHolder(view);
            }
            default: throw new IllegalStateException("Unknown item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_TEXT: {
                TextViewHolder textHolder = (TextViewHolder) holder;
                NotificationItem item = items.get(position);

                textHolder.name.setText(item.getName());
                textHolder.message.setText(item.getMessage());
                textHolder.userIcon.setImageResource(item.getUserIcon() == 0 ? R.drawable.invite_default_icon : item.getUserIcon());
                textHolder.time.setText(item.getTime());

                break;
            }

            case TYPE_PROMO: {
                PromoViewHolder promoHolder = (PromoViewHolder) holder;
                NotificationItem item = items.get(position);

                promoHolder.promoImage.setImageResource(item.getPhomoImage());
                promoHolder.title.setText(item.getTitle());
                promoHolder.time.setText(item.getTime());
                changeCodePart(promoHolder.description, item.getDescription(), item.getCode());

                break;
            }
            default: throw new IllegalStateException("Unknown item type");
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private void changeCodePart(TextView description, String text, String code) {
        int coloredPart1Index = text.indexOf(code);

        Spannable spannable = new SpannableString(text);
        if (coloredPart1Index != -1 && context != null) {
            Typeface typeface = TypefaceUtils.load(context.getAssets(), "fonts/OpenSans-Bold.ttf");

            spannable.setSpan(new CalligraphyTypefaceSpan(typeface),
                    coloredPart1Index,
                    coloredPart1Index + code.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        description.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        ImageView userIcon;
        TextView name;
        TextView message;
        TextView time;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            userIcon = itemView.findViewById(R.id.notification_icon_container);
            name = itemView.findViewById(R.id.item_notification_title);
            message = itemView.findViewById(R.id.item_notification_message);
            time = itemView.findViewById(R.id.notification_time);
        }
    }

    class PromoViewHolder extends RecyclerView.ViewHolder {
        ImageView promoImage;
        TextView title;
        TextView description;
        TextView time;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);

            promoImage = itemView.findViewById(R.id.notification_promo);
            title = itemView.findViewById(R.id.notification_promo_title);
            description = itemView.findViewById(R.id.notification_promo_description);
            time = itemView.findViewById(R.id.notification_time);
        }
    }
}