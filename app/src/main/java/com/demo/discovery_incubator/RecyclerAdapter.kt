package com.demo.discovery_incubator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class RecyclerAdapter(val items: ArrayList<CardItem>, val weakContext: WeakReference<Context>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = items.get(position).title
        holder.itemSubtitle.text = items.get(position).subtitle
        Picasso.with(weakContext.get()).load(items.get(position).image).into(holder.itemImage);
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemSubtitle: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemSubtitle = itemView.findViewById(R.id.item_subtitle)

            itemView.setOnClickListener {
                val position: Int = adapterPosition

                Toast.makeText(
                    itemView.context,
                    "${items.get(position).title} has been pressed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}