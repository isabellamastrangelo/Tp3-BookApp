package com.example.booksapp

//import com.bumptech.glide.Glide
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private lateinit var viewModel: DescriptionViewModel
    private lateinit var viewModelBookList: BookListViewModel
    private lateinit var v : View
    private lateinit var title : TextView
    private lateinit var description : TextView

    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var booksList: ArrayList<Books>

    private lateinit var idBookActual: String

    private lateinit var adapter : Adapter2

    private lateinit var recBooks : RecyclerView

    private lateinit var buttonEdit : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_description, container, false)
        title = v.findViewById(R.id.titleBook)
        description = v.findViewById(R.id.descriptionBook)

        buttonEdit = v.findViewById(R.id.buttonEdit2)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            db.collection("Books").document(data1).get().addOnSuccessListener {

                title.text = (it.data?.get("title").toString())
                description.text = (it.data?.get("description").toString())

            }.addOnFailureListener {
                Toast.makeText(context, "No se encontraron datos", Toast.LENGTH_SHORT).show()
            }
        }
        return v;
    }

    private fun initRecyclerView() {
        db.collection("Books").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val book:Books? = data.toObject<Books>(Books::class.java)
                    booksList.add(book!!)
                }

                adapter = Adapter2(booksList,
                    onDeleteClick = {position -> deleteBook(position) },
                    onEditClick = {position -> editBook(position) })
                recBooks.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }

    }

    fun editBook(position: Int) {
        idBookActual = booksList[position].idBook.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idBookActual

        findNavController().navigate(R.id.action_descriptionFragment_to_editFragment)
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
        viewModel = ViewModelProvider(requireActivity()).get(DescriptionViewModel::class.java)
        viewModelBookList = ViewModelProvider(requireActivity()).get(BookListViewModel::class.java)

        //title.text = viewModelBookList.bookTitle
        //description.text = viewModelBookList.bookDescription

    }

}