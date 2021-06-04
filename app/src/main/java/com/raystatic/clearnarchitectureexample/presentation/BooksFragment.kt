package com.raystatic.clearnarchitectureexample.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.raystatic.clearnarchitectureexample.R
import com.raystatic.clearnarchitectureexample.databinding.FragmentBooksBinding
import dagger.hilt.android.AndroidEntryPoint

class BooksFragment:Fragment(R.layout.fragment_books) {

    private var _binding: FragmentBooksBinding?=null
    private val binding: FragmentBooksBinding get() = _binding!!

    private val vm: BooksViewModel by viewModels {
        BooksViewModel.BooksViewModelFactory(
            ((requireActivity().application) as BaseApplication).getBooksUseCase,
            ((requireActivity().application) as BaseApplication).getBookmarksUseCase,
            ((requireActivity().application) as BaseApplication).bookmarkBooksUseCase,
            ((requireActivity().application) as BaseApplication).unbookmarkBookUseCase,
            ((requireActivity().application) as BaseApplication).bookWithStatusMapper,
        )
    }
    private lateinit var booksAdapter: BooksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentBooksBinding.bind(view)

        println("rahulray: fragment")

        booksAdapter = BooksAdapter(
            bookmark = {
                vm.bookmark(it)
            },
            unbookmark = {
                vm.unbookmark(it)
            }
        )

        binding.rvBooks.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2)
            adapter = booksAdapter
        }

        vm.error.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireContext(),
                "Error: $it",
                Toast.LENGTH_SHORT
            ).show()
        })

        vm.dataLoading.observe(viewLifecycleOwner, { loading ->
            when (loading) {
                true -> LayoutUtils.crossFade(binding.pbLoading, binding.rvBooks)
                false -> LayoutUtils.crossFade(binding.rvBooks, binding.pbLoading)
            }
        })

        vm.books.observe(viewLifecycleOwner, {
            booksAdapter.submitData(it)
        })

        vm.getBooks("Robert C. Martin")

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}