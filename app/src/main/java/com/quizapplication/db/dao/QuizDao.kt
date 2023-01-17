package com.quizapplication.db.dao

import androidx.room.*
import com.quizapplication.db.model.Quiz


@Dao
interface QuizDao {

    @Query("SELECT * FROM student_quiz_table")
    fun getQuizQuestions():List<Quiz>

    @Query("SELECT * FROM student_quiz_table WHERE id=:id")
    fun getSingle(id: Int): Quiz

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuizQuestion(quiz:Quiz)

    @Query("SELECT (SELECT COUNT(*) FROM student_quiz_table) == 0")
    fun isEmpty(): Boolean

    @Delete
    suspend fun delete(quiz:Quiz)

    @Query("DELETE FROM student_quiz_table")
    fun deleteAll()


}