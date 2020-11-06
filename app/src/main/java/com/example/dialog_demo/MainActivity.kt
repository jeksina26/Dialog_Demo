package com.example.dialog_demo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_dialog.*
import kotlinx.android.synthetic.main.user_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var btndateclick: Button? = null
    var tvtextdate: TextView? = null
    var cal = Calendar.getInstance()


    var country = arrayOf(
        "India", "Brazil", "Argentina",
        "Portugal", "France", "England", "Italy"
    )
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvtextdate = this.tvtext
        btndateclick = this.btndate

        btnalert.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Are You Sure")
            builder.setMessage("Do u want to close the app ?")
            builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
                finish()
            })
            builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        btndate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@MainActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

        btntime.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                this,
                OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    var selectedHour = selectedHour
                    val am_pm: String
                    if (selectedHour > 12) {
                        selectedHour = selectedHour - 12
                        am_pm = "PM"
                    } else {
                        am_pm = "AM"
                    }
                    tvtext.setText("$selectedHour:$selectedMinute $am_pm")
                },
                hour,
                minute,
                false
            )
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
            false
        }

        btnsinglechoice.setOnClickListener {
            var colorlist = arrayOf("Pink Color", "Red Color", "Black Color", "Orange Color", "Blue Color", "Sky Color", "Neon Color")

            var builder = AlertDialog.Builder(this)
            builder.setTitle("Select Color")

            builder.setSingleChoiceItems(colorlist, -1,
                { dialogInterface, which ->
                    tvtext.text = colorlist[which]
                }
            )

            builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
            builder.setNegativeButton("cancel", { dialog, id ->
                dialog.dismiss()
            })

            val alertDialog = builder.create()
            alertDialog.show()
        }

        btnlist.setOnClickListener {
            var colorlist = arrayOf("Pink", "Red","Purple","Brown", "Black", "Orange", "Blue", "Sky", "Neon")

            var builder = AlertDialog.Builder(this)
            builder.setTitle("Select Color")

            builder.setItems(colorlist){ dialog, which ->
                tvtext.text = colorlist[which]
            }

            var dialog = builder.create()
            dialog.show()
        }

        btncustom.setOnClickListener {

            var dialogview = LayoutInflater.from(this).inflate(R.layout.user_dialog,null)
            var builder = AlertDialog.Builder(this)
            builder.setView(dialogview)
            builder.setTitle("Enter Name")

            var alertDialog = builder.show()

            dialogview.btnsubmit.setOnClickListener {
                alertDialog.dismiss()

                var name = dialogview.ettext.text

                tvtext.setText("Name Is : " + name)
            }

            dialogview.btncancle.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }

        private fun updateDateInView() {
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            tvtext.text = sdf.format(cal.getTime())
        }
    }
