package com.demonhunter.todonoteskotlinversion

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.demonhunter.todonoteskotlinversion.db.NotesDatabase

//to access the database this class is created
class NotesApp:Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }
    fun getNotesDb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }

}
