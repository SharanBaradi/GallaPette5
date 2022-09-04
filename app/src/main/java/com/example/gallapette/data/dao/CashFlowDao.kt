package com.example.gallapette.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gallapette.data.model.CashFlow

@Dao
interface CashFlowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(cashflow : CashFlow)

    @Update
    suspend fun updateTransaction(cashflow : CashFlow)

    @Delete
    suspend fun deleteTransaction(cashflow: CashFlow)

    @Query("Delete From cashflow_table")
    suspend fun deleteAllTransactions()

    @Query("SELECT * From cashflow_table Order BY offsetDateTime Desc")
    fun readAllData(): LiveData<List<CashFlow>>

    @Query("Select distinct Payer as Names From cashflow_table Union Select distinct Beneficiary as Names From cashflow_table")
     suspend fun readDistinctPayers() : List<String>

    @Query("Select distinct PayerAccount as Names From cashflow_table Union Select distinct BeneficiaryAccount as Names From cashflow_table")
    suspend fun readDistinctAccounts() : List<String>


}