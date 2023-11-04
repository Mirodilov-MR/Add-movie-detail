package com.example.movieapp.room
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Movies")
data class Movies (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var author: String,
    var definition: String,
    var date: String
) :Parcelable