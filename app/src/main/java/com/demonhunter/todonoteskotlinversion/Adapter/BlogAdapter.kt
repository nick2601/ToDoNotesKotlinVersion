package com.demonhunter.todonoteskotlinversion.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demonhunter.todonoteskotlinversion.Model.Data
import com.demonhunter.todonoteskotlinversion.R

class BlogAdapter(private val list: List<Data>) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val tv_desc: TextView = itemView.findViewById(R.id.tv_description)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.blog_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = list[position]
        Log.e("blogAdapter", blog.img_url)
        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageView)
        holder.tv_title.text = blog.title
        holder.tv_desc.text = blog.description
    }
}