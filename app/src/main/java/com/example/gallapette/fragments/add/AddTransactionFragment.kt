package com.example.gallapette.fragments.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gallapette.R
import com.example.gallapette.data.model.CashFlow
import com.example.gallapette.data.viewmodel.CashFlowViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*
import android.widget.EditText as EditText1

class AddTransactionFragment : Fragment() {

    private lateinit var mCashFlowViewModel: CashFlowViewModel

    lateinit var buttonCredit: Button
    lateinit var buttonDebit: Button

    lateinit var et_Payer: AutoCompleteTextView
    lateinit var et_PayerAccount: EditText1
    lateinit var et_Beneficiary: AutoCompleteTextView
    lateinit var et_BeneficiaryAccount: EditText1
    lateinit var et_Description: EditText1
    lateinit var et_Amount: EditText1

    lateinit var spinner_PayerAccount: Spinner
    lateinit var spinner_BeneficiaryAccount: Spinner
    lateinit var iB_CheckPayerAccount: ImageButton
    lateinit var iB_CheckBeneficiaryAccount: ImageButton

    lateinit var accountHints: ArrayList<String>

    lateinit var textView_DisplayTimestamp: TextView
    lateinit var textView_DisplayDateTimeHeader: TextView
    lateinit var imageButton_EditDateTime: ImageButton
    lateinit var textView_Date: TextView
    lateinit var textView_Time: TextView
    lateinit var imageButton_EditDate: ImageButton
    lateinit var imageButton_EditTime: ImageButton
    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_transaction, container, false)

        mCashFlowViewModel = ViewModelProvider(this).get(CashFlowViewModel::class.java)

        //initializing views
        buttonCredit = view.findViewById(R.id.button_Credit1)
        buttonDebit = view.findViewById(R.id.button_Debit1)

        val buttonSubmitTransactionDetails1: Button =
            view.findViewById(R.id.button_SubmitTransactionDetails1)

        et_Payer = view.findViewById(R.id.editText_Payer1)

        et_PayerAccount = view.findViewById(R.id.editText_PayerAccount1)
        spinner_PayerAccount = view.findViewById(R.id.spinner_PayerAccount1)
        iB_CheckPayerAccount = view.findViewById(R.id.imageButton_CheckedPayerAccount1)

        et_Beneficiary = view.findViewById(R.id.editText_Beneficiary1)

        et_BeneficiaryAccount = view.findViewById(R.id.editText_BeneficiaryAccount1)
        spinner_BeneficiaryAccount = view.findViewById(R.id.spinner_BeneficiaryAccount1)
        iB_CheckBeneficiaryAccount = view.findViewById(R.id.imageButton_CheckedBeneficiaryAccount1)

        et_Description = view.findViewById(R.id.editText_Description1)
        et_Amount = view.findViewById(R.id.editText_Amount1)


        textView_DisplayTimestamp = view.findViewById(R.id.textView_DisplayTimestamp)
        textView_DisplayDateTimeHeader = view.findViewById(R.id.textView_DisplayDateTimeHeader)
        imageButton_EditDateTime = view.findViewById(R.id.imageButton_EditDateAndTime)
        textView_Date = view.findViewById(R.id.editText_SelectedDate)
        textView_Time = view.findViewById(R.id.editText_SelectedTime)
        imageButton_EditDate = view.findViewById(R.id.imageButton_EditDate)
        imageButton_EditTime = view.findViewById(R.id.imageButton_EditTime)

        setDefaultTransactionDataOnClick()
        buttonDebit.performClick()

        setDateAndTime()
        showDateAndTimeEditorOnClick()

        //Auto Complete Feature
        setAutoCompletionData()

        //Set Spinner
        getSpinnerData()
        setSpinnerData(spinner_PayerAccount, "") // you can set default as Cash
       // setSpinnerData(spinner_BeneficiaryAccount, "Cash")
        spinnerMethod(spinner_PayerAccount, et_PayerAccount, iB_CheckPayerAccount)
        spinnerMethod(spinner_BeneficiaryAccount, et_BeneficiaryAccount, iB_CheckBeneficiaryAccount)

        buttonSubmitTransactionDetails1.setOnClickListener {
            insertTransactionDataToDatabase()
        }
        return view
    }

    //Validate user new payment data & Insert to DB
    private fun insertTransactionDataToDatabase() {
        println("insertTransactionDataToDatabase")
//        val cash = CashFlow(0, 0, "", "", "", "", "")


        //Assign the latest values to cashFlow Transaction Object
        val cash = CashFlow(
            0,
            if (!et_Amount.text.toString().isNullOrBlank()) et_Amount.text.toString().trim()
                .toInt() else -1,
            et_Payer.text.toString().trim(),
            spinner_PayerAccount.selectedItem.toString().trim(),
            et_Beneficiary.text.toString().trim(),
            spinner_BeneficiaryAccount.selectedItem.toString().trim(),
            et_Description.text.toString().trim(),
            convertCalenderToOffsetDateTime()
        )

        //Get the latest values of transaction Object
//        cash.Payer = et_Payer.text.toString().trim()
//        cash.PayerAccount =
//            spinner_PayerAccount.selectedItem.toString().trim() //et_PayerAccount.text.toString()
//        cash.Beneficiary = et_Beneficiary.text.toString().trim()
//        cash.BeneficiaryAccount = spinner_BeneficiaryAccount.selectedItem.toString().trim()
//        cash.Description = et_Description.text.toString().trim()
//        cash.Amount =
//            if (!et_Amount.text.toString().isNullOrBlank()) et_Amount.text.toString().trim()
//                .toInt() else -1
//        cash.offsetDateTime = convertCalenderToOffsetDateTime()

        if (preValidateInput(cash)) {
            mCashFlowViewModel.addTransaction((cash))
            customToastLog("Added Successfully")
            //Navigate Back to List Fragment
            findNavController().navigate(R.id.action_addTransactionFragment_to_listTransactionsFragment)
        }
    }

    private fun preValidateInput(cashflow: CashFlow): Boolean {

        if (cashflow.Payer.isNullOrBlank()) {
            customToastLog("Please enter a valid  Payer Name")
            return false
        }
        if (cashflow.PayerAccount.isNullOrBlank()|| cashflow.PayerAccount=="Add New Type") {
            customToastLog("Please enter a valid Payer Account")
            return false
        }
        if (cashflow.Beneficiary.isNullOrBlank()) {
            customToastLog("Please enter a valid Beneficiary Name")
            return false
        }
        if (cashflow.BeneficiaryAccount.isNullOrBlank() || cashflow.BeneficiaryAccount=="Add New Type") {
            customToastLog("Please enter a valid BeneficiaryAccount")
            return false
        }
        if (cashflow.Amount <= 0) {
            //Todo() - Make this to <= 0 Later
            customToastLog("Please enter a valid Amount")
            return false
        }
        return true
    }

    //Debit - Credit Click Listeners
    private fun setDefaultTransactionDataOnClick() {

        //On Click Listeners
        buttonDebit.setOnClickListener {
            buttonDebit.setBackgroundColor(Color.CYAN)
            buttonCredit.setBackgroundColor(Color.WHITE)

            et_Payer.setText("Sharan Baradi")
//            et_PayerAccount.setText("Cash")
            et_Beneficiary.setText("")
//            et_BeneficiaryAccount.setText("Cash")
        }

        buttonCredit.setOnClickListener {
            buttonCredit.setBackgroundColor(Color.CYAN)
            buttonDebit.setBackgroundColor(Color.WHITE)

            et_Payer.setText("")
//            et_PayerAccount.setText("Cash")
            et_Beneficiary.setText("Sharan Baradi")
//            et_BeneficiaryAccount.setText("Cash")
        }
    }

    private fun setAutoCompletionData() {
        val nameHints: List<String> = mCashFlowViewModel.readDistinctPayers()
        val payerAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nameHints)

        println("Name Hints :")
        printList(nameHints)

        et_Payer.setAdapter(payerAdapter)
        et_Beneficiary.setAdapter(payerAdapter)
    }

    // Set Spinners for Payer & Benificiary Account
    private fun getSpinnerData() {
        accountHints = mCashFlowViewModel.readDistinctAccounts()
        val defaultList = listOf<String>("Default","Cash", "Card", "Online", "Others", "Add New Type")
        for (item in defaultList) {
            if (!accountHints.contains(item)) accountHints.add(item)
        }
        //printList(accountHints)
    }

    private fun setSpinnerData(mySpinner: Spinner, defaultValue: String) {
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            accountHints)
        mySpinner.adapter = adapter
        //Set Provided Default Value for provided Spinner
        if (!defaultValue.isNullOrBlank() && accountHints.contains(defaultValue)) {
            mySpinner.setSelection(accountHints.indexOf(defaultValue))
        }
    }

    private fun spinnerMethod(mySpinner: Spinner, myEditText: EditText1, myImageButton: ImageButton) {
        mySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                println("Selected " + mySpinner.selectedItem.toString())
                if (mySpinner.selectedItem.toString() == "Add New Type") {
                    mySpinner.visibility = View.INVISIBLE
                    myEditText.visibility = View.VISIBLE
                    myImageButton.visibility = View.VISIBLE
                } else if (mySpinner == spinner_PayerAccount) {
                    setSpinnerData(spinner_BeneficiaryAccount,
                        spinner_PayerAccount.selectedItem.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        myImageButton.setOnClickListener() {
            val newAccount = myEditText.text.toString()
            if (!accountHints.contains(newAccount)) accountHints.add(newAccount)
            setSpinnerData(mySpinner, newAccount)
            myEditText.visibility = View.INVISIBLE
            myImageButton.visibility = View.INVISIBLE
            mySpinner.visibility = View.VISIBLE
        }
    }

    //Set Payment Date & Time using Calender
    private fun showDateAndTimeEditorOnClick() {

        imageButton_EditDateTime.setOnClickListener() {
            if (textView_Date.visibility == View.INVISIBLE) {
                textView_Date.visibility = View.VISIBLE
                textView_Time.visibility = View.VISIBLE
                imageButton_EditDate.visibility = View.VISIBLE
                imageButton_EditTime.visibility = View.VISIBLE
                imageButton_EditDateTime.setBackgroundResource(R.drawable.ic_baseline_check_24)

                textView_DisplayTimestamp.visibility = View.INVISIBLE
                textView_DisplayDateTimeHeader.visibility = View.INVISIBLE

            } else {
                textView_Date.visibility = View.INVISIBLE
                textView_Time.visibility = View.INVISIBLE
                imageButton_EditDate.visibility = View.INVISIBLE
                imageButton_EditTime.visibility = View.INVISIBLE
                imageButton_EditDateTime.setBackgroundResource(R.drawable.ic_baseline_edit_24)

                textView_DisplayTimestamp.visibility = View.VISIBLE
                textView_DisplayDateTimeHeader.visibility = View.VISIBLE
            }
        }
    }

    private fun setDateAndTime() {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                updateDisplayDateAndTime()
            }
        }

        imageButton_EditDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(), dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
        updateDateInView()

        val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                updateTimeInView()
                updateDisplayDateAndTime()
            }
        }

        imageButton_EditTime.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                TimePickerDialog(requireContext(), timeSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false).show()
            }
        })
        updateTimeInView()

        updateDisplayDateAndTime()
    }

    private fun updateDisplayDateAndTime() {
        // mention the format you need
        val sdfDateTimeFormat = SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US)
        val displayTimeStr = sdfDateTimeFormat.format(cal.getTime())
        println(displayTimeStr)
        textView_DisplayTimestamp.text = displayTimeStr

        /*
        val localDateTime = LocalDateTime.of(cal.get(Calendar.YEAR),
            (cal.get(Calendar.MONTH) + 1),
            cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.HOUR),
            cal.get(Calendar.MINUTE),
            cal.get(Calendar.SECOND),
            cal.get(Calendar.MILLISECOND))
        println("Converted localDateTime : " + localDateTime)

        println(cal.time)
        val timeStamp: Timestamp = Timestamp(cal.timeInMillis)
        println("Timestamp :" + timeStamp)

        val longValue: Long = cal.timeInMillis
        println("longValue :" + longValue)

        val convertedDate: Date = Date(longValue)
        println("convertedDate : " + convertedDate)

        val convertedCal: Calendar = Calendar.getInstance()
        convertedCal.set(Calendar.DAY_OF_MONTH, convertedDate.date)
        convertedCal.set(Calendar.MONTH, convertedDate.month)
        convertedCal.set(Calendar.YEAR, convertedDate.year)
        println("convertedCal" + convertedCal.time)

        println("Cal ZoneOffset :" )
//      println(ZoneOffset.ofHours(cal.get(Calendar.ZONE_OFFSET)))

        var convertedDateTimeOffSet: OffsetDateTime //= OffsetDateTime.now()

        convertedDateTimeOffSet =
            OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(5))
        println("convertedDateTimeOffSet : " + convertedDateTimeOffSet.toString())

        convertedDateTimeOffSet = OffsetDateTime.of(localDateTime, OffsetDateTime.now().offset)
        println("convertedDateTimeOffSet : " + convertedDateTimeOffSet.toString())

        val myOffsetFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        val formattedOffsetString = convertedDateTimeOffSet.format(myOffsetFormatter)
        println("Formatted Offset String : " + formattedOffsetString)

        val extractedOffsetTime = myOffsetFormatter.parse(formattedOffsetString,OffsetDateTime::from)
        println("extractedOffsetTime : "+extractedOffsetTime)

        println(cal.get(Calendar.ZONE_OFFSET).toString())
        println(OffsetDateTime.now().offset.toString())

*/
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView_Date.setText(sdf.format(cal.getTime()))
    }

    private fun updateTimeInView() {
        val myFormat = "hh:mm aa"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView_Time.setText(sdf.format(cal.getTime()))
    }

    private fun convertCalenderToOffsetDateTime(): OffsetDateTime {
        //get locadatetime from calender (similar for date conversion)
        var localDateTime: LocalDateTime = Instant.ofEpochMilli(cal.getTime().time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        println("Converted localDateTime : " + localDateTime)
        val convertedDateTimeOffSet = OffsetDateTime.of(localDateTime, OffsetDateTime.now().offset)
        println("convertedDateTimeOffSet : " + convertedDateTimeOffSet.toString())

        return convertedDateTimeOffSet
    }

    //Print & Toast Methods
    private fun customToastLog(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        println(msg)
    }
    private fun printList(listArray: List<Any>) {
        for (item in listArray) {
            println(item.toString())
        }
    }
}


