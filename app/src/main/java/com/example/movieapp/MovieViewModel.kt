package com.example.movieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.room.MovieDatabase
import com.example.movieapp.room.Movies
import kotlinx.coroutines.launch

class MovieViewModel (application: Application) : AndroidViewModel(application){
    val repository: MovieRepository
    init {
        val dao = MovieDatabase.getDatabaseInstance(application).moviesDao()
        repository = MovieRepository(dao)
    }
    fun addMovies(movies: Movies){
        repository.insertMovies(movies)
    }
    fun updateMovies(movies: Movies){
        viewModelScope.launch {
            repository.updateMovie(movies)
        }
    }
    fun getAllMovies(): LiveData<List<Movies>> = repository.getAllMovies()
}