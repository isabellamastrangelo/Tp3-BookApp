package com.example.booksapp

import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class BookListViewModel : ViewModel() {
    lateinit var bookTitle : String
    lateinit var bookDescription : String
}