package com.example.notes2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context : Context) : SQLiteOpenHelper(context , DATABASE_NAME , null , DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "notes_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTES = "notes_table"

        private const val KEY_ID = "_id"
        private const val KEY_TEXT = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_NOTES_TABLE = ("CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT"
                + ")")
        db?.execSQL(CREATE_NOTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES)
        onCreate(db)
    }

    fun addNote(note : Note): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TEXT , note.text)

        val success = db.insert(TABLE_NOTES , null , contentValues)
        db.close()
        return success
    }

    fun getAllNotes() : ArrayList<Note>{

        var allNotes = ArrayList<Note>()
        val selectQuery = "SELECT * FROM $TABLE_NOTES"

        val db = this.readableDatabase

        var cusror : Cursor? = null

        try {
            cusror = db.rawQuery(selectQuery , null)
        } catch(e : Exception){
            db.execSQL(selectQuery)
            return allNotes
        }

        var text : String
        var id : Int

        cusror.moveToFirst()

        if (cusror.moveToFirst()) {
            do {

                text = cusror.getString(cusror.getColumnIndex(KEY_TEXT))
                id  = cusror.getInt(cusror.getColumnIndex(KEY_ID))

                val note = Note(text , id)
                allNotes.add(note)
            } while (cusror.moveToNext())
        }
        return allNotes
    }

    fun delete(note : Note): Int {
        val db = this.writableDatabase
        val contenValues  = ContentValues()
        contenValues.put(KEY_TEXT , note.text)
        contenValues.put(KEY_ID , note.id)
        val res = db.delete(TABLE_NOTES , KEY_ID+"="+note.id , null)
        return res
    }


























}