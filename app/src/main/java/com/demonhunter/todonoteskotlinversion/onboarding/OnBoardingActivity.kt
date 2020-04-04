package com.demonhunter.todonoteskotlinversion.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.demonhunter.todonoteskotlinversion.R
import com.demonhunter.todonoteskotlinversion.utils.PrefConst
import com.demonhunter.todonoteskotlinversion.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick,OnBoardingTwoFragment.OnOptionClick {
    lateinit var viewPager: ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindView()
        setupSharedPreferences()
    }

    private fun setupSharedPreferences() {
        sharedPreferences=getSharedPreferences(PrefConst.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) //the mode should be private because the data should be shared by only this app
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter=FragmentAdapter(supportFragmentManager)
        viewPager.adapter=adapter
    }

    override fun onClick() {
        viewPager.currentItem=1
    }

    override fun onOptionBack() {
        viewPager.currentItem=0
    }

    override fun onOptionDone() {
        //2nd fragment
        editor=sharedPreferences.edit()
        editor.putBoolean(PrefConst.ON_BOARDED_SUCCESSFULLY,true)
        editor.apply()

        val intent =Intent(this@OnBoardingActivity,LoginActivity::class.java)
        startActivity(intent)
    }
}

//1st time users-splash->OnBoarding->Login->MyNotes
//2 nd time users-splash->login->MyNotes
