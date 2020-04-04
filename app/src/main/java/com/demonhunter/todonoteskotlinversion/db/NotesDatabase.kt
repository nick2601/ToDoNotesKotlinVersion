package com.demonhunter.todonoteskotlinversion.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//abstract class to create a database
@Database(entities = [Notes::class],version = 1)
abstract class NotesDatabase :RoomDatabase(){
    abstract fun notesDao():NotesDao
    companion object{                                                       //we can access this variable outside of its class
    lateinit var INSTANCE :NotesDatabase
        fun getInstance(context: Context):NotesDatabase{
            synchronized(NotesDatabase::class){                              //only one thread can access the whole db class and no other can access while it is in execution
                INSTANCE=Room.databaseBuilder(context.applicationContext,NotesDatabase::class.java,"my-notes.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

}