package com.example.gallapette

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        val buttonTest : Button = binding.findViewById(R.id.button_Test)
        val buttonNewTransaction : Button = binding.findViewById(R.id.button_NewTransaction)
        val buttonNewTransactionRoom : Button = binding.findViewById(R.id.button_NewTransactionRoom)

        buttonNewTransaction.setOnClickListener {
           // view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToTransactiondetailsFragment())
        }

        buttonTest.setOnClickListener {
            view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToListTransactionsComposeFragment())
        }

        buttonNewTransactionRoom.setOnClickListener {
            view?.findNavController()?.navigate(HomeFragmentDirections.actionHomeFragmentToListTransactionsFragment())
        }

        return binding
    }
}