package com.demonhunter.todonoteskotlinversion.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demonhunter.todonoteskotlinversion.R
import com.demonhunter.todonoteskotlinversion.clicklisteners.ItemClickListeners
import com.demonhunter.todonoteskotlinversion.db.Notes

class NotesAdapter(private val list: List<Notes>, private val itemClickListeners: ItemClickListeners) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    val TAG ="NotesAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val notes = list[position]
        val title = notes.title
        val description = notes.description
        holder.tv_title.text = title
        holder.tv_desc.text = description
        holder.checkBoxMarkStatus.isChecked=notes.isTaskCompleted
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                itemClickListeners.onClick(notes)
            }

        })
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted=isChecked
                Log.d(TAG,isChecked.toString())
                itemClickListeners.onUpdate(notes)
            }
        })
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val tv_desc: TextView = itemView.findViewById(R.id.tv_description)
        val checkBoxMarkStatus: CheckBox = itemView.findViewById(R.id.checkboxMarkStatus)
        val imageView:ImageView=itemView.findViewById(R.id.imageView)

    }
}