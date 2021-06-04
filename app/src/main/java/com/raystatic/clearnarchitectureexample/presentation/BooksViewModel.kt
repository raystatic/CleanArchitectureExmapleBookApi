package com.raystatic.clearnarchitectureexample.presentation

import androidx.lifecycle.*
import com.raystatic.clearnarchitectureexample.entities.BookWithStatus
import com.raystatic.clearnarchitectureexample.mappers.BookWithStatusMapper
import com.raystatic.domain.common.Result
import com.raystatic.domain.entities.Volume
import com.raystatic.domain.usecases.BookmarkBookUseCase
import com.raystatic.domain.usecases.GetBookmarksUseCase
import com.raystatic.domain.usecases.GetBooksUseCase
import com.raystatic.domain.usecases.UnbookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BooksViewModel(
    private val getBooksUseCases:GetBooksUseCase,
    private val getBookmarksUseCase:GetBookmarksUseCase,
    private val bookmarkBookUseCase:BookmarkBookUseCase,
    private val unbookmarkUseCase: UnbookmarkUseCase,
    private val mapper:BookWithStatusMapper
):ViewModel(){

    private val _dataLoading = MutableLiveData(true)
    val dataLoading:LiveData<Boolean> = _dataLoading

    private val _books = MutableLiveData<List<BookWithStatus>>()
    val books:LiveData<List<BookWithStatus>> = _books

    private val _error = MutableLiveData<String>()
    val error:LiveData<String> = _error

    private val _remoteBooks = arrayListOf<Volume>()

    fun getBooks(author:String){
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when(val booksResult = getBooksUseCases.invoke(author)){
                is Result.Success ->{
                    _remoteBooks.clear()
                    _remoteBooks.addAll(booksResult.data)

                    val bookmarksFlow = getBookmarksUseCase.invoke()
                    bookmarksFlow.collect {
                        _books.value = mapper.fromVolumeToBookWithStatus(_remoteBooks,it)
                        _dataLoading.postValue(false)
                    }

                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    _books.value = emptyList()
                    _error.postValue(booksResult.exception.message  )
                }
            }
        }
    }

    fun bookmark(bookWithStatus: BookWithStatus){
        viewModelScope.launch {
            bookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(bookWithStatus))
        }
    }

    fun unbookmark(bookWithStatus: BookWithStatus){
        viewModelScope.launch {
            unbookmarkUseCase.invoke(mapper.fromBookWithStatusToVolume(bookWithStatus))
        }
    }


    class BooksViewModelFactory(
        private val getBooksUseCase: GetBooksUseCase,
        private val getBookmarksUseCase: GetBookmarksUseCase,
        private val bookmarkBookUseCase: BookmarkBookUseCase,
        private val unbookmarkBookUseCase: UnbookmarkUseCase,
        private val mapper: BookWithStatusMapper
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BooksViewModel(
                getBooksUseCase,
                getBookmarksUseCase,
                bookmarkBookUseCase,
                unbookmarkBookUseCase,
                mapper
            ) as T
        }
    }

}