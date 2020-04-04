package com.demonhunter.todonoteskotlinversion.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.demonhunter.todonoteskotlinversion.Adapter.BlogAdapter
import com.demonhunter.todonoteskotlinversion.Model.JsonResponse
import com.demonhunter.todonoteskotlinversion.R


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBlogs: RecyclerView
    val TAG = "BlogActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()

    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(JsonResponse::class.java,
                object :
                    ParsedRequestListener<JsonResponse> {   //here we are the mapping the json response from the url to json response model class
                    override fun onResponse(response: JsonResponse?) {
                        setupRecyclerView(response!!)
                    }

                    override fun onError(anError: ANError?) {
                        //
                    }
                })
    }

    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setupRecyclerView(response: JsonResponse) {
        val blogAdapter = BlogAdapter(response.data)
        val linearLayoutManager = LinearLayoutManager(this@BlogActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }
}
