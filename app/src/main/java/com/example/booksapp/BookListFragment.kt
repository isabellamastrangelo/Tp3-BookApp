package com.example.booksapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.booksapp.BookAdapter
import android.content.ContentValues
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.booksapp.Books

import com.example.booksapp.BookListViewModel
import com.google.android.material.snackbar.Snackbar

//import com.google.android.play.core.integrity.v


@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class BookListFragment : Fragment() {
    private lateinit var viewModel: BookListViewModel
    private lateinit var v : View
    private lateinit var recBooks : RecyclerView
   // private var repository = BooksRepository()
    private lateinit var booksList: ArrayList<Books>

    var db = Firebase.firestore
    private lateinit var buttonAdd: Button
    private lateinit var idBookActual: String
    private lateinit var idCompartido: sharedData

    private lateinit var adapter : BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.book_list_fragment, container, false)
        recBooks = v.findViewById(R.id.recBooks)

        db = FirebaseFirestore.getInstance()
        recBooks.layoutManager = LinearLayoutManager(context)
        booksList = arrayListOf()
        buttonAdd = v.findViewById(R.id.buttonAdd)

        initRecyclerView()

        buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_bookListFragment_to_postBookFragment)
        }
        return v
    }

    private fun initRecyclerView() {
        db.collection("Books").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val book:Books? = data.toObject<Books>(Books::class.java)
                    booksList.add(book!!)
                }

                adapter = BookAdapter(booksList,
                    onDeleteClick = {position -> deleteBook(position) },
                    onEditClick = {position -> editBook(position) },
                    onItemClick = {position -> seeBookData(position)} )

                recBooks.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seeBookData(position:Int) {
        val numero = booksList[position].toString()
        idBookActual = booksList[position].idBook.toString()
        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idBookActual

        Snackbar.make(v, numero , Snackbar.LENGTH_SHORT).show()

         findNavController().navigate(R.id.action_bookListFragment_to_descriptionFragment)

    }
    fun editBook(position: Int) {
        idBookActual = booksList[position].idBook.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idBookActual

        findNavController().navigate(R.id.action_bookListFragment_to_editFragment)
    }

    fun deleteBook (position : Int){

        db.collection("Books").document(booksList[position].idBook.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Book deleted", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                booksList.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"Error in deleting book", Toast.LENGTH_SHORT).show() }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BookListViewModel::class.java]
        // TODO: Use the ViewModel
    }
}

