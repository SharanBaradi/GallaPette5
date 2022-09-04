package com.example.gallapette.database

import com.example.gallapette.Objects.CashFlowObject

class CreateSQLQuery {

     suspend fun createNewCashFlowInsertQuery(cashFlowObject: CashFlowObject): String {
        val CashFlowTableInsertQuery: String = """ INSERT INTO [dbo].[CashFlow1]
           ([Amount]
           ,[Payer]
           ,[PayerAccount]
           ,[Beneficiary]
           ,[BeneficiaryAccount]
           ,[Description])
     VALUES
           ('${cashFlowObject.Amount}',
           '${cashFlowObject.Payer}',
           '${cashFlowObject.PayerAccount}',
           '${cashFlowObject.Beneficiary}',
           '${cashFlowObject.BeneficiaryAccount}',
           '${cashFlowObject.Description}'
           )
           """
        println(CashFlowTableInsertQuery)
        return CashFlowTableInsertQuery
    }

    suspend fun createCashFlowSelectQuery(): String {
        val customerQuery = """ SELECT
            [Amount]
           ,[Payer]
           ,[PayerAccount]
           ,[Beneficiary]
           ,[BeneficiaryAccount]
           ,[Description]
           
            FROM [dbo].[CashFlow1]
            """
        return customerQuery
    }
}