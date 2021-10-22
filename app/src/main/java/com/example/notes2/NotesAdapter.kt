package com.example.notes2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val listener : INotesItemClick) : RecyclerView.Adapter<NotesViewHolder>() {

    var allNotes = ArrayList<Note>()

    fun updateNotes(notesList : ArrayList<Note>){
        allNotes.clear()
        allNotes.addAll(notesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item , parent , false)
        val viewHolder = NotesViewHolder(v)

        viewHolder.deleteIV.setOnClickListener {
            listener.delete(allNotes[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.textView.text = allNotes[position].text
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }


}

class NotesViewHolder(v : View) : RecyclerView.ViewHolder(v)
{
    val textView : TextView = v.findViewById(R.id.text)
    val deleteIV : ImageView  = v.findViewById(R.id.iv_delete)
}


interface INotesItemClick{
    fun delete(note : Note)
}

















