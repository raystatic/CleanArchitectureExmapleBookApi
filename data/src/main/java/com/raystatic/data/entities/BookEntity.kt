package com.raystatic.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(

    @PrimaryKey
    val id:String,
    val title:String,
    val authors:List<String>,
    val imageUrl:String?
)