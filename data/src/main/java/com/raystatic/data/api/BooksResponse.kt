package com.raystatic.data.api

data class BooksResponse(
    val items:List<Item>
)

data class Item(
    val id:String,
    val volumeInfo: ApiVolumeInfo
)

data class ApiVolumeInfo(
    val title:String,
    val authors:List<String>,
    val imageLinks:ImageLinks?
)

data class ImageLinks(val smallThumbnail:String, val thumbnail:String)