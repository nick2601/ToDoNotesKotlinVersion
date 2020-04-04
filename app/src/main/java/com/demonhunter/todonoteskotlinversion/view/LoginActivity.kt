package com.demonhunter.todonoteskotlinversion.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.demonhunter.todonoteskotlinversion.R
import com.demonhunter.todonoteskotlinversion.utils.AppConstant
import com.demonhunter.todonoteskotlinversion.utils.PrefConst
import com.demonhunter.todonoteskotlinversion.utils.StoreSession


class LoginActivity : AppCompatActivity() {
    lateinit var editTextfullName: EditText
    lateinit var editTextuserName: EditText
    lateinit var btnLogin: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setupSharedPreference()
    }


    private fun setupSharedPreference() {
        sharedPreferences =
            getSharedPreferences(PrefConst.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun bindViews() {
        btnLogin=findViewById(R.id.logbutton)
        editTextfullName = findViewById(R.id.fullName)
        editTextuserName = findViewById(R.id.Uname)
        val clickAction = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val Uname = editTextuserName.text.toString()
                val fullName = editTextfullName.text.toString()
                if (fullName.isNotEmpty() && Uname.isNotEmpty()) {
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME, fullName)
                    startActivity(intent)
                    saveFullName(fullName)
                    saveLoginStatus()
                } else {

                }

            }
        }
        btnLogin.setOnClickListener(clickAction)
    }

    private fun saveLoginStatus() {
        StoreSession.write(PrefConst.IS_LOGGED_IN,true)
    }

    private fun saveFullName(fullName: String) {
       StoreSession.write(PrefConst.FULL_NAME,fullName)
    }


}
