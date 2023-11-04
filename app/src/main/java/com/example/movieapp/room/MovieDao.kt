package com.example.movieapp.room
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Query("Select * from Movies")
    fun getAllMovies(): LiveData<List<Movies>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: Movies)

    @Delete
    fun delete(movies: Movies)

    @Update
    fun update(movies: Movies)

}