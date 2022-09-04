package com.example.gallapette.Objects

data class CashFlowObject(
    //var TransactionID : Int,
    var Amount: Int,
    var Payer: String,
    var PayerAccount: String,
    var Beneficiary: String,
    var BeneficiaryAccount: String,
    var Description: String,
//  var TransactionDate: Date,
)


/*
SELECT TOP (1000) [TransactionID]

      ,[Amount]
      ,[Payer]
      ,[PayerAccount]
      ,[Payee]
      ,[PayeeAccount]
      ,[Description]

      ,[TransactionDate]
  FROM [AndroidTestDB].[dbo].[CashFlow1]*/
