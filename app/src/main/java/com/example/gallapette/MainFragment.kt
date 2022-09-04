package com.example.gallapette

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.gallapette.databinding.ActivityMainBinding
import com.example.gallapette.databinding.FragmentMainBinding
import com.google.android.material.color.MaterialColors.getColor
import kotlinx.coroutines.Dispatchers

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding: ViewDataBinding? = FragmentMainBinding.inflate((inflater, container, false))
//            //DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

//        val binding = FragmentMainBinding.inflate(inflater, container, false)
//        val root = binding.root

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater, R.layout.fragment_main, container, false)

//        val binding1= FragmentMainBinding.inflate(inflater,container,false)
//        binding1.buttonCredit.setTextColor()
//        val binding2 = ActivityMainBinding.inflate(layoutInflater)
//        binding2.

//        val buttonDebit : Button =

//        binding.buttonDebit.setOnClickListener(){
//            Toast.makeText(context, "Hey", Toast.LENGTH_SHORT).show()
//            binding.buttonDebit.setTextColor(0xFFFFFF)
//            binding.buttonCredit.setTextColor(0xFFFFFF)
//        }

        return binding?.root
    }
}


///*
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
//*/
///**
// * A simple [Fragment] subclass.
// * Use the [MainFragment.newInstance] factory method to
// * create an instance of this fragment.
// *//*
//
//class MainFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
//    }
//
//    companion object {
//        */
///**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MainFragment.
//         *//*
//
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MainFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}*/
