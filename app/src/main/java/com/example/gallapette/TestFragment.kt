package com.example.gallapette

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_test.*
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

class TestFragment : Fragment() {


    lateinit var textView_test : TextView

    lateinit var testDate : TextView
    lateinit var  testTime : TextView
    lateinit var  testDate_btn : ImageButton
    lateinit var  testTime_btn : ImageButton

    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_test, container, false)

        val spinner:Spinner = binding.findViewById(R.id.spinner_test)
        val array : List<String> = listOf("1","20")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array)
        spinner.adapter = adapter

        textView_test = binding.findViewById(R.id.textView_testString)
        var testSting : String
        var currentDateTime : LocalDateTime = LocalDateTime.now()

        println(currentDateTime.toString())
        //textView_test.setText(currentDateTime.toString())

         testDate  = binding.findViewById(R.id.editText_TestDate)
         testTime  = binding.findViewById(R.id.editText_TestTime)
         testDate_btn  = binding.findViewById(R.id.imageButton_TestEditDate)
         testTime_btn  = binding.findViewById(R.id.imageButton_TestEditTime)


//testDate.doAfterTextChanged {
//    val editedDate : String  = testDate.text.toString()
//    if(editedDate.length == 10) {
//        cal.set(Calendar.YEAR, editedDate.substring(0, 1).toInt())
//        cal.set(Calendar.MONTH, editedDate.substring(3, 4).toInt())
//        cal.set(Calendar.DAY_OF_MONTH, editedDate.substring(6, 9).toInt())
//    }
//}
        setDateAndTime()

        println(cal.get(Calendar.DATE))
        textView_test.text = cal.get(Calendar.DATE).toString()

        return binding
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView_test.text = sdf.format(cal.getTime())
        testDate.setText(sdf.format(cal.getTime()))
    }

    private fun updateTimeInView() {
        val myFormat = "hh : mm aa" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView_test.text = sdf.format(cal.getTime())
        testTime.setText(sdf.format(cal.getTime()))
    }

    private fun setDateAndTime(){
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        testDate_btn.setOnClickListener(object  : View.OnClickListener {
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
            }
        }
        testTime_btn.setOnClickListener(object  : View.OnClickListener {
            override fun onClick(view: View) {

                TimePickerDialog(requireContext(), timeSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false).show()
            }
        })
        updateTimeInView()
    }


}
/*
    println(currentDateTime.format(DateTimeFormatter.BASIC_ISO_DATE))
*//*
	20180125
*//*

// DateTimeFormatter.ISO_DATE_TIME is default format
    println(currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
*//*
	2018-01-25T13:46:52.125
*//*

    println(currentDateTime.format(DateTimeFormatter.ISO_DATE))
*//*
	2018-01-25
*//*

    println(currentDateTime.format(DateTimeFormatter.ISO_TIME))
*//*
	13:52:16.716
*//*
//
// Just get current Date
//
    println(currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
*//*
	1/25/18
*//*

    println(currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
*//*
	Jan 25, 2018
*//*

    println(currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
*//*
	January 25, 2018
*//*

    println(currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)))
*//*
	Thursday, January 25, 2018
*//*

//
// Just get current Time
//
    println(currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))
*//*
	2:11 PM
*//*

    println(currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)))
*//*
	2:11:12 PM
*//*

//
// Get both current Date & Time
//
    println(currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)))
*//*
	1/25/18 2:14 PM
*//*

    println(currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM)))
*//*
	Thursday, January 25, 2018 2:14:58 PM
*//*

    val currentTime = LocalTime.now()
    println(currentTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)))


    var date = LocalDate.parse("2018-12-12")
//        The parse() method by default uses the standard date format yyyy-MM-dd.
//
//        We can also pass our own format to parse a date String:

    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    date = LocalDate.parse("31-12-2018", formatter)

//        LocalDate provides many methods that we can use to extract specific components from Date.
//        Some of these are quite trivial such as extracting the year, month or day from a Date:

    date = LocalDate.parse("2018-12-31")
//        assertThat(date.year).isEqualTo(2018)
//        assertThat(date.month).isEqualTo(Month.DECEMBER)
//        assertThat(date.dayOfMonth).isEqualTo(31)
////        We can also extract other information like era, dayOfTheWeek or dayOfTheMonth:
//
//        assertThat(date.era.toString()).isEqualTo("CE")
//        assertThat(date.dayOfWeek).isEqualTo(DayOfWeek.MONDAY)
//        assertThat(date.dayOfYear).isEqualTo(365)*/
