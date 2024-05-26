package com.example.musicmingle

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.musicmingle.model.Data


class MusicAdapter(
    private val musicData: List<Data>,
    private val context: Context,
    private val onItemClick: (String, Boolean) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private var isPlaying = false

    inner class MusicViewHolder(itemView: View) : ViewHolder(itemView) {
        val image_view = itemView.findViewById<ImageView>(R.id.image_view)
        val title = itemView.findViewById<TextView>(R.id.music_titleTv)
        val playBtn = itemView.findViewById<ImageButton>(R.id.playBtn)
        val pauseBtn = itemView.findViewById<ImageButton>(R.id.pauseBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.music_card, parent, false)
        return MusicViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return musicData.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val single_item = musicData[position]
        holder.title.text = single_item.album.title
        Glide.with(context).load(single_item.album.cover_medium).centerInside()
            .into(holder.image_view)

        holder.playBtn.setOnClickListener {
            isPlaying = !isPlaying
            onItemClick(single_item.preview, isPlaying)
            setColor(isPlaying)
        }

        holder.pauseBtn.setOnClickListener {
            isPlaying = false
            onItemClick(single_item.preview, isPlaying)
        }
    }

    fun setColor(isPlaying: Boolean): Int {
        if (isPlaying) {
            return Color.GREEN
        } else {
            return Color.WHITE
        }
    }
}