package com.example.gallapette.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime


//@Parcelize
@Parcelize
@Entity(tableName = "cashflow_table")
data class CashFlow (

    @PrimaryKey(autoGenerate = true)
    var TransactionID : Int,
    var Amount: Int,
    var Payer: String,
    var PayerAccount: String,
    var Beneficiary: String,
    var BeneficiaryAccount: String,
    var Description: String,
    var offsetDateTime : OffsetDateTime = OffsetDateTime.now(),

    ) : Parcelable