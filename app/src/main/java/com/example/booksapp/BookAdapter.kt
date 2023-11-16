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
import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookAdapter(
    booksList: ArrayList<Books>,
    private val onItemClick: (Int) -> Unit

) : RecyclerView.Adapter<BookAdapter.BooksHolder>() {
    private var booksList: ArrayList<Books>
    init {
        this.booksList = booksList
    }
    class BooksHolder (v: View) : RecyclerView.ViewHolder(v) {
        val title= v.findViewById<TextView>(R.id.txtTitleBooks)
        val description= v.findViewById<TextView>(R.id.descriptionBook)


        fun render(booksModel: Books){
            title.text = booksModel.title
            //description.text = booksModel.description

            //el error es:     java.lang.NullPointerException
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.itembooks,parent,false)
        return (BooksHolder(v))
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        val item = booksList[position]
        holder.render(item)
        print(item.toString())
        if (item != null) {
            holder.itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

}


