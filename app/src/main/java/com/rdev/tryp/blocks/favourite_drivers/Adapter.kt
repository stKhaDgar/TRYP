package com.rdev.tryp.blocks.favourite_drivers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.rdev.tryp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val drivers: MutableList<FavoriteDriver>, private val context: Context?, private val onItemClickListener: FavouriteDriversFragment.OnItemClickListener) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_favourite_driver, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val model = drivers[i]

        holder.title.text = model.title
        holder.desc.text = model.category

        if (model.isLike) {
            holder.likeImg.setImageResource(R.drawable.ic_like)
        } else {
            holder.likeImg.setImageResource(R.drawable.ic_like_off)
        }

        if (model.photo != null) {
            holder.pbLoader?.visibility = View.VISIBLE
            holder.pbLoader?.playAnimation()

            Picasso.get().load(model.photo).into(holder.imageView, object : Callback {
                override fun onSuccess() {
                    holder.pbLoader?.visibility = View.GONE
                    holder.pbLoader?.clearAnimation()
                }

                override fun onError(e: Exception) {
                    holder.pbLoader?.visibility = View.GONE
                    holder.pbLoader?.clearAnimation()
                    holder.imageView?.setImageResource(R.drawable.img)
                }
            })
        } else {
            holder.imageView?.setImageResource(R.drawable.img)
        }
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

    inner class MyViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var title: TextView = itemView.findViewById(R.id.title)
        var desc: TextView = itemView.findViewById(R.id.desc)
        var imageView = itemView.findViewById(R.id.img) as? ImageView
        var likeImg: ImageView = itemView.findViewById(R.id.like_img)
        private var likeLayout: FrameLayout = itemView.findViewById(R.id.like_layout)
        var pbLoader = itemView.findViewById(R.id.pb_loader) as? LottieAnimationView

        init {
            likeLayout.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            likeImg.setImageResource(R.drawable.ic_like_off)
            onItemClickListener.onItemClick(v, adapterPosition)
        }
    }

    fun delete(position: Int) {
        drivers.removeAt(position)
        notifyItemRemoved(position)
    }

    fun like(position: Int) {
        drivers[position].isLike = true
        notifyItemChanged(position)
    }

    fun disLike(position: Int) {
        drivers[position].isLike = false
        notifyItemChanged(position)
    }

}