package com.demonhunter.todonoteskotlinversion.clicklisteners

import com.demonhunter.todonoteskotlinversion.db.Notes

interface ItemClickListeners {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)                                               //when we click the check box we update the value of the list
}