package com.example.notes2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesItemClick {



    lateinit var allNotes : ArrayList<Note>
    lateinit var adapter : NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NotesAdapter(this)

        btn_submit.setOnClickListener {
            addNote()
            editText.setText("")
        }

        recycler_view.layoutManager = LinearLayoutManager(this)

        recycler_view.adapter = adapter

        allNotes = ArrayList()
        getNotes()



    }

    private fun addNote() {
        val text = editText.text.toString()
        if (text != "") {
            val dh = DatabaseHandler(this)
            val res = dh.addNote(Note(text , allNotes.size))
            allNotes = dh.getAllNotes()
            adapter.updateNotes(allNotes)
            if (res == -1L) Toast.makeText(this , "Failed to add note" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNotes() {
        val dh = DatabaseHandler(this)
        allNotes.clear()
        allNotes = dh.getAllNotes()
        adapter.updateNotes(allNotes)
    }

    override fun delete(note: Note) {
        val dh = DatabaseHandler(this)
        dh.delete(note)
        allNotes = dh.getAllNotes()
        adapter.updateNotes(allNotes)
    }

}