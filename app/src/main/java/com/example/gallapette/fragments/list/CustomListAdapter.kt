package com.example.gallapette.fragments.list

import com.example.gallapette.data.model.CashFlow

class CustomListAdapter {

    private var transactionList = emptyList<CashFlow>()


    fun setCashFlowUpdatedData(cashflow: List<CashFlow>) {
        this.transactionList = cashflow
//    notifyDataSetChanged()
    }
}

