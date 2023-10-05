package com.example.booksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView


class BookAdapter(
    var booksList: MutableList<Books>,
    var onClick: (Books) -> Unit
) : RecyclerView.Adapter<BookAdapter.BooksHolder>() {
    class BooksHolder (v: View) : RecyclerView.ViewHolder(v) {
        private var view: View


        init {
            this.view = v
        }

        fun setTitle (title : String) {
            var txtTitle: TextView = view.findViewById(R.id.txtTitleBooks)
            txtTitle.text = title
        }

        fun getItem () : ConstraintLayout {
            return view.findViewById(R.id.itemLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.itembooks,parent,false)
        return (BooksHolder(view))
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        holder.setTitle(booksList[position].title)
        holder.getItem().setOnClickListener {
            print("Click en título")
            onClick(booksList[position])
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

}