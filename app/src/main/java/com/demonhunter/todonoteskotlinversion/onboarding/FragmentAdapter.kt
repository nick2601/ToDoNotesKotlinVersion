package com.demonhunter.todonoteskotlinversion.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

//FragmentStatePageAdapter basically handles the state of the fragment.If we are the opening the first fragment the second fragment will not be visible & vice versa
//Fragment Manager is responsible for the activities of the fragments
@Suppress("DEPRECATION")
class FragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        //0,1
        if (position == 0) return OnBoardingOneFragment()
        else if (position == 1) return OnBoardingTwoFragment()
        return getItem(position)
    }

    override fun getCount(): Int {
        return 2
    }

}