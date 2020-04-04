package com.demonhunter.todonoteskotlinversion.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.demonhunter.todonoteskotlinversion.R


class OnBoardingOneFragment : Fragment() {
    lateinit var tV_Next: TextView
    lateinit var onNextClick: OnNextClick

    override fun onAttach(context: Context) {//it is used to initialise onNextClick variable which was created as an  interface
                                            // and added it in onAttach
        super.onAttach(context)
        onNextClick=context as OnNextClick  //typecasting is done
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.This method is used to initialise all the widgets from the given layout file.
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //this method is used to link the widgets from the above xml file with the view for given layout fragment.
        bindViews(view)
    }

    private fun bindViews(view: View) {
        tV_Next = view.findViewById(R.id.tv_Next)
        clickListeners()

    }

    private fun clickListeners() {
        tV_Next.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            onNextClick.onClick()
            }
        })
    }
    interface OnNextClick{
        fun onClick()
    }
}
