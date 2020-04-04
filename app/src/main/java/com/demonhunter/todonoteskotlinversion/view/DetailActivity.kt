package com.demonhunter.todonoteskotlinversion.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.demonhunter.todonoteskotlinversion.utils.AppConstant
import com.demonhunter.todonoteskotlinversion.R

class DetailActivity : AppCompatActivity() {
    val TAG ="DetailActivity"
    lateinit var tv_title:TextView
    lateinit var tv_description:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setIntentdata()
    }

    private fun setIntentdata() {
        val intent = intent //getIntent()
        val title =intent.getStringExtra(AppConstant.TITLE)
        val description =intent.getStringExtra(AppConstant.DESCRIPTION)
        //setText
        tv_title.text = title
        tv_description.text=description

    }

    private fun bindViews() {
        tv_title=findViewById(R.id.tv_title)
        tv_description=findViewById(R.id.tv_description)


    }
}
