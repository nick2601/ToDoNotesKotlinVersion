package com.demonhunter.todonoteskotlinversion.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.demonhunter.todonoteskotlinversion.R

//Thumb rule=whenever we inflate something we use view
class OnBoardingTwoFragment : Fragment() {
    lateinit var tV_Back: TextView
    lateinit var tv_Done: TextView
    lateinit var onOptionClick:OnOptionClick


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick =context as OnOptionClick   //typecasting is done
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        tV_Back=view.findViewById(R.id.tv_Back)
        tv_Done=view.findViewById(R.id.tv_Done)
        clickListeners()
    }

    private fun clickListeners() {
        tV_Back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
            onOptionClick.onOptionBack()
            }

        })
        tv_Done.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
            onOptionClick.onOptionDone()
            }

        })
    }
    interface OnOptionClick{
        fun onOptionBack()
        fun onOptionDone()
    }


}
