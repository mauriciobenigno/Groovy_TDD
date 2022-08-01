package br.com.mauriciobenigno.groovy_tdd

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import br.com.mauriciobenigno.groovy_tdd.databinding.PlaylistItemBinding


class MyPlaylistRecyclerViewAdapter(
    private val values: List<Playlist>
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            PlaylistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Playlist = values[position]
        holder.playlistName.text = item.name
        holder.playlistCategory.text = item.category
        holder.playlistImage.setImageResource(item.image)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: PlaylistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val playlistName: TextView = binding.playlistsName
        val playlistCategory: TextView = binding.playlistsCategory
        val playlistImage: ImageView = binding.playlistsImage

    }

}