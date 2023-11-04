package com.example.movieapp

import androidx.lifecycle.LiveData
import com.example.movieapp.room.MovieDao
import com.example.movieapp.room.Movies

class MovieRepository(val dao: MovieDao) {
    fun getAllMovies(): LiveData<List<Movies>>{
        return dao.getAllMovies()
    }
    fun insertMovies(movies: Movies){
        dao.insertMovie(movies)
    }
    fun updateMovie(movies: Movies){
        dao.update(movies)
    }
}