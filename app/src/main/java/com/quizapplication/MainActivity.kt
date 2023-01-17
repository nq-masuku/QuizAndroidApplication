package com.quizapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quizapplication.db.dao.AppDatabase
import com.quizapplication.db.model.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var appDb : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appDb = AppDatabase.getDatabase(this)

        //writeData()
        //dropTable()

    }

    private fun writeData(){
        val quizDetails: Array<String> = resources.getStringArray(R.array.quiz_details)

        GlobalScope.launch(Dispatchers.IO) {
            for(question in quizDetails){
                var ques = question.split(",")
                var quiz = Quiz(null, ques[0], ques[1],ques[2],ques[3],ques[4],ques[5])
                launch {
                    appDb.quizDao().addQuizQuestion(quiz)
                }
            }
        }

        Toast.makeText(this,"Successfully added records",Toast.LENGTH_SHORT).show()
    }

    private fun dropTable() {
        GlobalScope.launch(Dispatchers.IO) {
            appDb.quizDao().deleteAll()
        }

    }
}