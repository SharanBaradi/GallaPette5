package com.example.gallapette.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gallapette.R
import com.example.gallapette.data.model.CashFlow
import com.example.gallapette.data.viewmodel.CashFlowViewModel
import kotlinx.android.synthetic.main.fragment_update_transaction.*
import kotlinx.android.synthetic.main.fragment_update_transaction.view.*

class UpdateTransactionFragment : Fragment() {
    private lateinit var mCashFlowViewModel: CashFlowViewModel

    private val args by navArgs<UpdateTransactionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_transaction, container, false)

        mCashFlowViewModel = ViewModelProvider(this).get(CashFlowViewModel::class.java)

        //Set views value as safe args, passed from listTransactions
        view.editText_UpdatePayer.setText(args.currentTransaction.Payer)
        view.editText_UpdatePayerAccount.setText(args.currentTransaction.PayerAccount)
        view.editText_UpdateBeneficiary.setText(args.currentTransaction.Beneficiary)
        view.editText_UpdateBeneficiaryAccount.setText(args.currentTransaction.BeneficiaryAccount)
        view.editText_UpdateDescription.setText(args.currentTransaction.Description)
        view.editText_UpdateAmount.setText(args.currentTransaction.Amount.toString())

        view.button_UpdateSubmitTransactionDetails.setOnClickListener {
            updateItem()
        }

        //Enable Menu in this fragment
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        //TODO() implement check Input

        val Payer: String = editText_UpdatePayer.text.toString()
        val PayerAccount: String = editText_UpdatePayerAccount.text.toString()
        val Beneficiary: String = editText_UpdateBeneficiary.text.toString()
        val BeneficiaryAccount: String = editText_UpdateBeneficiaryAccount.text.toString()
        val Description: String = editText_UpdateDescription.text.toString()
        val Amount: Int = editText_UpdateAmount.text.toString().toInt()

        val updatedTransactionItem = CashFlow(args.currentTransaction.TransactionID,
            Amount, Payer, PayerAccount, Beneficiary, BeneficiaryAccount, Description)

        //update current transaction
        mCashFlowViewModel.updateTransaction(updatedTransactionItem)

        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
        //Nav back

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete)
            deleteTransaction()
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTransaction() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mCashFlowViewModel.deleteTransaction(args.currentTransaction)
            Toast.makeText(requireContext(),
                "Successfully removed transaction ${args.currentTransaction.TransactionID}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateTransactionFragment_to_listTransactionsFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentTransaction.TransactionID}?")
        builder.setMessage("Are you sure you want to delete this transaction item?  ${args.currentTransaction.TransactionID}")
        builder.create().show()
    }
}