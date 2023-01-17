package com.quizapplication.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_quiz_table")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name="question") val question: String?,
    @ColumnInfo(name="choice_a") val a: String?,
    @ColumnInfo(name="choice_b") val b: String?,
    @ColumnInfo(name="choice_c") val c: String?,
    @ColumnInfo(name="choice_d") val d: String?,
    @ColumnInfo(name="ques_answer") val answer: String?

)

