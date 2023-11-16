package com.example.booksapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
//import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Adapter2(
    booksList: ArrayList<Books>,
    val onDeleteClick : (Int)->Unit,
    val onEditClick : (Int) -> Unit,
    //val onItemClick: (Int) -> Unit

) : RecyclerView.Adapter<Adapter2.BooksHolder2>() {
    private var booksList: ArrayList<Books>
    init {
        this.booksList = booksList
    }
    class BooksHolder2 (v: View) : RecyclerView.ViewHolder(v) {

        val editar = v.findViewById<Button>(R.id.buttonDelete2)
        val eliminar = v.findViewById<Button>(R.id.buttonEdit2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder2 {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_description,parent,false)
        return (BooksHolder2(v))
    }

    override fun onBindViewHolder(holder: BooksHolder2, position: Int) {
        val item = booksList[position]
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.editar.setOnClickListener {
            onEditClick(position)
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

}