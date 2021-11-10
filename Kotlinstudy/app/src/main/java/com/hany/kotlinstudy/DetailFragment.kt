package com.hany.kotlinstudy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hany.kotlinstudy.databinding.FragmentDetailBinding
import com.hany.kotlinstudy.databinding.FragmentListBinding


class DetailFragment : Fragment() {
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater,container,false)
        binding.btnBack.setOnClickListener{ mainActivity.goBack()}
        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

}