package com.example.gallapette.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gallapette.R
import com.example.gallapette.data.model.CashFlow
import kotlinx.android.synthetic.main.custom_transactionrow.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var transactionList = emptyList<CashFlow>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_transactionrow, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = transactionList[position]

        holder.itemView.findViewById<TextView>(R.id.textView_IdItem).text =
            currentItem.TransactionID.toString()
        holder.itemView.findViewById<TextView>(R.id.textView_DescriptionItem).text =
            currentItem.Description
        holder.itemView.findViewById<TextView>(R.id.textView_AmountItem).text =
            currentItem.Amount.toString()

//        val formatter : Formatter = Formatter("hh:mm", Locale.US)
//        val sdfDateTimeFormat = ("MM/dd/yyyy hh:mm aa", Locale.US)
        holder.itemView.findViewById<TextView>(R.id.textView_ListViewDate).text = //sdfDateTimeFormat.format(currentItem.offsetDateTime.toLocalDateTime()).toString()
            currentItem.offsetDateTime.toLocalDate().toString() + " " + currentItem.offsetDateTime.toLocalTime().toString()

        //Credit or Debit Logic
        if (currentItem.Beneficiary == "Sharan Baradi") {
            holder.itemView.findViewById<TextView>(R.id.textView_AmountItem).setTextColor((0xE128BA00).toInt())
            holder.itemView.findViewById<TextView>(R.id.textView_PayerItem).text = "Credited by "
            holder.itemView.findViewById<TextView>(R.id.textView_BenificiaryItem).text =
                currentItem.Payer
        } else {
            holder.itemView.findViewById<TextView>(R.id.textView_AmountItem).setTextColor((0xFFFF0000).toInt())
            holder.itemView.findViewById<TextView>(R.id.textView_PayerItem).text = "Paid to "
            holder.itemView.findViewById<TextView>(R.id.textView_BenificiaryItem).text = currentItem.Beneficiary
        }

        holder.itemView.rowTransactionLayout.setOnClickListener {
            val action =
                ListTransactionsFragmentDirections.actionListTransactionsFragmentToUpdateTransactionFragment(
                    currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

  //  fun TextView.setTextColor(color: Long) = this.setTextColor(color.toInt())

    override fun getItemCount(): Int {
        return transactionList.size
    }

    fun setData(cashflow: List<CashFlow>) {
        this.transactionList = cashflow
        notifyDataSetChanged()
    }
}