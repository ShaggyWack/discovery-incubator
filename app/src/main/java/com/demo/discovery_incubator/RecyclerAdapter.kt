package com.demo.discovery_incubator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.lang.ClassCastException

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {

    private var items: ArrayList<CardItem> = arrayListOf(
        CardItem("Title 1", "Subtitle 1", R.drawable.dummy_image),
        CardItem("Title 2", "Subtitle 2", R.drawable.dummy_image),
        CardItem("Title 3", "Subtitle 3", R.drawable.dummy_image),
        CardItem("Title 4", "Subtitle 4", R.drawable.dummy_image),
        CardItem("Title 5", "Subtitle 5", R.drawable.dummy_image),
        CardItem("Title 6", "Subtitle 6", R.drawable.dummy_image),
        CardItem("Title 7", "Subtitle 7", R.drawable.dummy_image),
        CardItem("Title 8", "Subtitle 8", R.drawable.dummy_image)
    )

    private var filteredItems: ArrayList<CardItem> = ArrayList<CardItem>()

    init {
        filteredItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindViewUsingFilteredItems(position)
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredItems = items
                } else {
                    val resultList = ArrayList<CardItem>()
                    for (row in items) {
                        if (row.title.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    filteredItems = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredItems
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                try {
                    filteredItems = results?.values as ArrayList<CardItem>
                    notifyDataSetChanged()
                }catch (e:ClassCastException){
                    // Leave filteredItems as is
                }
            }
        }
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
                    "${filteredItems.get(position).title} has been pressed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        fun bindViewUsingFilteredItems(position: Int){
            this.itemTitle.text = filteredItems.get(position).title
            this.itemSubtitle.text = filteredItems.get(position).subtitle
            this.itemImage.setImageResource(filteredItems.get(position).image)
        }
    }
}