package com.example.gallapette.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gallapette.data.database.CashFlowDatabase
import com.example.gallapette.data.model.CashFlow
import com.example.gallapette.data.repo.CashFlowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class CashFlowViewModel(application: Application)  : AndroidViewModel(application){
    val readAllData : LiveData<List<CashFlow>>

    private  val repository: CashFlowRepository

    init{
        val cashFlowDao = CashFlowDatabase.getDatabase(application).cashFlowDao()
        repository = CashFlowRepository(cashFlowDao)
        readAllData = repository.readALLData
    }

    fun addTransaction( cashFlow : CashFlow){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(cashFlow)
        }
    }

    fun updateTransaction(cashFlow: CashFlow){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransaction(cashFlow)
        }
    }

    fun deleteTransaction(cashFlow: CashFlow){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(cashFlow)
        }
    }

    fun deleteAllTransaction(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTransaction()
        }
    }

    fun readDistinctPayers() : List<String>{
        var distinctPayers : List<String> = listOf("Sharan")
        viewModelScope.launch(Dispatchers.IO) {
            distinctPayers = repository.readDistinctPayers()
        }
        sleep(50)
         return distinctPayers

//        return repository.readDistinctPayers()
    }

    fun readDistinctAccounts() : ArrayList<String>{
        var distinctAccounts : List<String> = listOf("Cash")

        viewModelScope.launch(Dispatchers.IO) {
            distinctAccounts = repository.readDistinctAccounts()
        }
        sleep(50)
        return ArrayList(distinctAccounts) //distinctAccounts
    }
}
