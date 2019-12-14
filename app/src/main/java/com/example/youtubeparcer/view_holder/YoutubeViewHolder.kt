package com.example.youtubeparcer.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeparcer.R

/**
 * Created by Karukes Sergey on
 */
class YouTubeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var image: ImageView? = null
    private var title: TextView? = null
    private var description: TextView? = null

    init {
        image = itemView.findViewById(R.id.image)
        title = itemView.findViewById(R.id.title)
        description = itemView.findViewById(R.id.description)
    }

    fun bind(item: String) {
        title?.text = item
    }

}