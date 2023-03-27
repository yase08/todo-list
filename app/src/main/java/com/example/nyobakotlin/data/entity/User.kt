package com.example.nyobakotlin.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) var uid: Int? = null,
    @ColumnInfo(name = "project_name") var projectName: String?,
    @ColumnInfo(name = "task_name") var taskName: String?,
    @ColumnInfo(name = "assign_to") var assignTo: String?,
    @ColumnInfo(name = "sprint") var sprint: Int?,
    @ColumnInfo(name = "start_date")var startDate: String?,
    @ColumnInfo(name = "end_date") var endDate: String?,
    @ColumnInfo(name = "attachment") var attachment: String?
)