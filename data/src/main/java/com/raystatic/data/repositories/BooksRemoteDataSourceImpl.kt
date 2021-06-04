package com.raystatic.data.repositories

import com.raystatic.data.api.BooksApi
import com.raystatic.data.mappers.BooksResponseMapper
import com.raystatic.domain.common.Result
import com.raystatic.domain.entities.Volume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class BooksRemoteDataSourceImpl(
    private val service:BooksApi,
    private val mapper: BooksResponseMapper
):BookRemoteDataSource {

    override suspend fun getBooks(author: String): Result<List<Volume>> {
        return withContext(Dispatchers.IO){
            try {
                val response = service.getBooks(author)
                if (response.isSuccessful){
                    return@withContext Result.Success(mapper.toVolumeList(response.body()!!))
                }else{
                    return@withContext Result.Error(Exception(response.message()))
                }
            }catch (e:Exception){
                return@withContext Result.Error(e)
            }
        }
    }
}