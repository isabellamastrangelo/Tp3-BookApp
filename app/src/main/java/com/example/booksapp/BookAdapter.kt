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

class BookAdapter(
    booksList: ArrayList<Books>,
     val onDeleteClick : (Int)->Unit,
     val onEditClick : (Int) -> Unit,
     val onItemClick: (Int) -> Unit

) : RecyclerView.Adapter<BookAdapter.BooksHolder>() {
    private var booksList: ArrayList<Books>
    init {
        this.booksList = booksList
    }
    class BooksHolder (v: View) : RecyclerView.ViewHolder(v) {
        val title= v.findViewById<TextView>(R.id.titleBook)
        val description= v.findViewById<TextView>(R.id.descriptionBook)

        val editar = v.findViewById<Button>(R.id.buttonEdit)
        val eliminar = v.findViewById<Button>(R.id.buttonEdit)

        fun render(booksModel: Books){
            title.text = booksModel.title
            description.text = booksModel.description


        }
        /*fun setTitle (title : String) {
            var txtTitle: TextView = view.findViewById(R.id.txtTitleBooks)
            txtTitle.text = title
        }

        fun getItem () : ConstraintLayout {
            return view.findViewById(R.id.itemLayout)
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.itembooks,parent,false)
        return (BooksHolder(v))
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        val item = booksList[position]
        holder.render(item)
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.editar.setOnClickListener {
            onEditClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }

        /*
        holder.setTitle(booksList[position].title)
        holder.getItem().setOnClickListener {
            print("Click en título")
            onClick(booksList[position])
        }*/
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

}