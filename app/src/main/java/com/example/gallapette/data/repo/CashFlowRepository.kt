package com.example.gallapette.data.repo

import androidx.lifecycle.LiveData
import com.example.gallapette.data.dao.CashFlowDao
import com.example.gallapette.data.model.CashFlow

class CashFlowRepository(private val cashFlowDao: CashFlowDao) {
    val readALLData:LiveData<List<CashFlow>> = cashFlowDao.readAllData()

    suspend fun addTransaction(cashFlow : CashFlow){
        cashFlowDao.addTransaction(cashFlow)
    }

    suspend fun updateTransaction(cashFlow: CashFlow){
        cashFlowDao.updateTransaction(cashFlow)
    }

    suspend fun deleteTransaction(cashFlow: CashFlow){
        cashFlowDao.deleteTransaction(cashFlow)
    }

    suspend fun deleteAllTransaction(){
        cashFlowDao.deleteAllTransactions()
    }

    suspend fun readDistinctPayers() : List<String>{
        return cashFlowDao.readDistinctPayers()
    }

    suspend fun readDistinctAccounts() : List<String>{
        return cashFlowDao.readDistinctAccounts()
    }

}