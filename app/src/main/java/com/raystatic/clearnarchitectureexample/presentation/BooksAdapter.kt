package com.raystatic.clearnarchitectureexample.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raystatic.clearnarchitectureexample.R
import com.raystatic.clearnarchitectureexample.databinding.ItemBookBinding
import com.raystatic.clearnarchitectureexample.entities.BookWithStatus
import com.raystatic.clearnarchitectureexample.entities.BookmarkStatus

class BooksAdapter(
    private val bookmark:(BookWithStatus) -> Unit,
    private val unbookmark:(BookWithStatus) -> Unit
):RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    inner class BooksViewHolder(private val binding: ItemBookBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bookWithStatus: BookWithStatus?){
            binding.apply {
                bookWithStatus?.let { book ->
                    book.imageUrl?.let {
                        Glide.with(itemView)
                            .load(it)
                            .into(ivBookCover)
                        tvBookName.text = ""
                        tvBookAuthors.text = ""
                    } ?: kotlin.run {
                        Glide.with(itemView)
                            .load(R.drawable.ic_launcher_background)
                            .into(ivBookCover)
                        tvBookName.text = book.title
                        tvBookAuthors.text = book.authors.joinToString()
                    }
                    ivBookmark.setOnClickListener {
                        bookmark(book)
                    }
                    ivUnbookmark.setOnClickListener {
                        unbookmark(book)
                    }

                    when (book.status) {
                        BookmarkStatus.BOOKMARKED -> {
                            ivBookmark.visibility = View.GONE
                            ivUnbookmark.visibility = View.VISIBLE
                        }
                        BookmarkStatus.UNBOOKMARKED -> {
                            ivBookmark.visibility = View.VISIBLE
                            ivUnbookmark.visibility = View.GONE
                        }
                    }

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<BookWithStatus>(){
        override fun areItemsTheSame(oldItem: BookWithStatus, newItem: BookWithStatus): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: BookWithStatus,
            newItem: BookWithStatus
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<BookWithStatus>) = differ.submitList(list)

}