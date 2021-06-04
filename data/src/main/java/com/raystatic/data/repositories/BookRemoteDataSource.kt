package com.raystatic.data.repositories

import com.raystatic.domain.common.Result
import com.raystatic.domain.entities.Volume

interface BookRemoteDataSource {

    suspend fun getBooks(author:String):Result<List<Volume>>

}