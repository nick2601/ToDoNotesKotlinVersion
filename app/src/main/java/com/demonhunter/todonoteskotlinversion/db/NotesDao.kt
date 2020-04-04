package com.demonhunter.todonoteskotlinversion.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//data access objects-DAO //we created 4 functions for query,update,insert, delete
@Dao
interface NotesDao {
    @Query("SELECT * from notesData")//getting all the data from the Notes table
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE) //we replace the repeated notes or item with a new item
    fun insert(notes:Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun deleteNotes(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted=:status")
    fun deleteNotes(status: Boolean)

}