package com.example.nyobakotlin

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.example.nyobakotlin.data.AppDatabase
import com.example.nyobakotlin.data.entity.User
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {
    private lateinit var projectName: EditText
    private lateinit var taskName: EditText
    private lateinit var assignTo: EditText
    private lateinit var sprint: EditText
    private lateinit var attachment: EditText
    private lateinit var editButton: Button
    private lateinit var startDate: TextView
    private lateinit var endDate: TextView
    private lateinit var database: AppDatabase
    private var startDateCalendar: Calendar = Calendar.getInstance()
    private var endDateCalendar: Calendar = Calendar.getInstance()

    private val startDateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
        startDateCalendar.set(Calendar.YEAR, year)
        startDateCalendar.set(Calendar.MONTH, monthOfYear)
        startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "dd-MMMM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        startDate.text = sdf.format(startDateCalendar.time)
    }

    private val endDateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
        endDateCalendar.set(Calendar.YEAR, year)
        endDateCalendar.set(Calendar.MONTH, monthOfYear)
        endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "dd-MMMM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        endDate.text = sdf.format(endDateCalendar.time)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        projectName = findViewById(R.id.projectName)
        taskName = findViewById(R.id.taskName)
        assignTo = findViewById(R.id.assignTo)
        sprint = findViewById(R.id.sprint)
        startDate = findViewById(R.id.startDate)
        endDate = findViewById(R.id.endDate)
        attachment = findViewById(R.id.layout_attachment)
        editButton = findViewById(R.id.editButton)

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if(intent != null) {
            val id = intent.getInt("id", 0)
            val user = database.userDao().get(id)

            projectName.setText(user.projectName)
            taskName.setText(user.taskName)
            assignTo.setText(user.assignTo)
            sprint.setText(user.sprint.toString())
            startDate.setText(user.startDate)
            endDate.setText(user.endDate)
            attachment.setText(user.attachment)

            editButton.setOnClickListener {
                if(projectName.text.isNotEmpty() && taskName.text.isNotEmpty() && assignTo.text.isNotEmpty() && sprint.text.isNotEmpty() && startDate.text.isNotEmpty() && endDate.text.isNotEmpty() && attachment.text.isNotEmpty()) {
                        // Edit data
                        database.userDao().update(
                            User(
                                intent.getInt("id", 0),
                                projectName.text.toString(),
                                taskName.text.toString(),
                                assignTo.text.toString(),
                                sprint.text.toString().toInt(),
                                startDate.text.toString(),
                                endDate.text.toString(),
                                attachment.text.toString()
                            )
                        )
                    finish()
                }
            }
        }

        startDate.setOnClickListener {
            DatePickerDialog(
                this, startDateListener,
                startDateCalendar.get(Calendar.YEAR),
                startDateCalendar.get(Calendar.MONTH),
                startDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        endDate.setOnClickListener {
            DatePickerDialog(
                this, endDateListener,
                endDateCalendar.get(Calendar.YEAR),
                endDateCalendar.get(Calendar.MONTH),
                endDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}