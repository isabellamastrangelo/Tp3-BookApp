package com.example.booksapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class DescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private lateinit var viewModel: DescriptionViewModel
    private lateinit var viewModelBookList: BookListViewModel
    private lateinit var v : View
    private lateinit var title : TextView
    private lateinit var description : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_description, container, false)
        title = v.findViewById(R.id.titleBook)
        description = v.findViewById(R.id.descriptionBook)
        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DescriptionViewModel::class.java)
        viewModelBookList = ViewModelProvider(requireActivity()).get(BookListViewModel::class.java)

        title.text = viewModelBookList.bookTitle
        description.text = viewModelBookList.bookDescription

    }

}