package com.demonhunter.todonoteskotlinversion.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//defines the structure of the data we are creating
@Entity(tableName = "notesData")//we have created a table here of name notesData
data class Notes(
    @PrimaryKey(autoGenerate = true) //used for generating unique id
    var id: Int? =null,
    @ColumnInfo(name = "title")
    var title: String="",
    @ColumnInfo(name = "description")
    var description:String="",
    @ColumnInfo(name = "imagePath")
    var imagePath:String="", //taking the path of img from our storage
    @ColumnInfo(name = "isTaskCompleted")
    var isTaskCompleted:Boolean=false //was our task listed in the Notes was completed or not
)
//We have created a Data class which take this 5 parameters