package com.example.gallapette.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallapette.R
import com.example.gallapette.data.viewmodel.CashFlowViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListTransactionsFragment : Fragment() {
    private lateinit var mCashFlowViewModel: CashFlowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_transactions, container, false)
        val floatingActionButton: FloatingActionButton =
            view.findViewById(R.id.floatingActionButton_AddTransaction1)

        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_ListTransactions)
        recyclerView.adapter = adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        //CashFlowViewModel
            mCashFlowViewModel = ViewModelProvider(this).get(CashFlowViewModel::class.java)
        mCashFlowViewModel.readAllData.observe(viewLifecycleOwner, Observer { cashflow ->
            adapter.setData(cashflow)
        })

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listTransactionsFragment_to_addTransactionFragment)
        }
        //Enable Menu in this fragment
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete)
            deleteAllTransactions()
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTransactions() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mCashFlowViewModel.deleteAllTransaction()
            Toast.makeText(requireContext(),
                "Successfully removed all transactions",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All Transactions?")
        builder.setMessage("Are you sure you want to delete all?")
        builder.create().show()
    }
}
