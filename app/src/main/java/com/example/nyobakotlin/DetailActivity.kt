package com.example.nyobakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.nyobakotlin.data.AppDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var projectName: TextView
    private lateinit var taskName: TextView
    private lateinit var assignTo: TextView
    private lateinit var sprint: TextView
    private lateinit var startDate: TextView
    private lateinit var endDate: TextView
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        projectName = findViewById(R.id.projectName)
        taskName = findViewById(R.id.taskName)
        assignTo = findViewById(R.id.assignTo)
        sprint = findViewById(R.id.sprint)
        startDate = findViewById(R.id.startDate)
        endDate = findViewById(R.id.endDate)
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
        }
    }
}