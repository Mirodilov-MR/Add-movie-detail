package com.example.movieapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MoviesDesignBinding
import com.example.movieapp.room.MovieDatabase
import com.example.movieapp.room.Movies

class MoviesAdapter(val context: Context, val list: List<Movies>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    lateinit var listener: OnUserClickedListener

    class ViewHolder(val binding: MoviesDesignBinding) : RecyclerView.ViewHolder(binding.root)

    private val dao = MovieDatabase.getDatabaseInstance(context).moviesDao()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MoviesDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = list[position]
        holder.binding.etName.text = movie.name
        holder.binding.etAuthor.text = movie.author
        holder.binding.etDate.text = movie.date

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("position_id", movie)
            holder.itemView.findNavController()
                .navigate(R.id.action_mainScreen_to_informationScreen, bundle)
        }


        holder.binding.btnDelete.setOnClickListener {
            dao.delete(list[position])
            notifyItemRemoved(position)
        }
        holder.binding.btnEdit.setOnClickListener {
            updateMovie(movie)
            val bundle = Bundle()
            bundle.putParcelable("position_id", movie)
            holder.itemView.findNavController()
                .navigate(R.id.action_mainScreen_to_editScreen, bundle)
            true
        }

    }

private fun updateMovie(movies: Movies){
    dao.update(movies)
}
    interface OnUserClickedListener {
        fun onUserClicked(position: Int)
    }
    fun setOnUserClickedListener(listener: OnUserClickedListener){
        this.listener = listener
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
