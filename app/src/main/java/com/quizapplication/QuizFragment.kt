package com.quizapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.quizapplication.db.dao.AppDatabase
import com.quizapplication.db.model.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class QuizFragment : Fragment()  {
    private lateinit var appDb : AppDatabase

    private lateinit var tvQuestion: TextView
    private lateinit var counter: TextView
    private lateinit var progress: TextView
    private lateinit var ans1: Button
    private lateinit var ans2: Button
    private lateinit var ans3: Button
    private lateinit var ans4: Button
    private var score:Int?=0
    private lateinit var questions: List<Quiz>
    private var selectedChoice: String? = null
    private lateinit var answers: MutableList<String>
    private lateinit var currentQuiz: Quiz
    private var appViewModel: AppViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        appDb = AppDatabase.getDatabase(view.context)
        tvQuestion = view.findViewById(R.id.question)
        counter = view.findViewById(R.id.counter)
        progress = view.findViewById(R.id.progress)

        ans1 = view.findViewById(R.id.ans1)
        ans2 = view.findViewById(R.id.ans2)
        ans3 = view.findViewById(R.id.ans3)
        ans4 = view.findViewById(R.id.ans4)
        val skipBtn = view.findViewById<Button>(R.id.button1)
        val nextBtn = view.findViewById<Button>(R.id.button2)
        val button = view.findViewById<Button>(R.id.button0)
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        button?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.quiz_to_homeFragment)
        }

        GlobalScope.launch(Dispatchers.IO) {
            questions = appDb.quizDao().getQuizQuestions()
            changeQuestion(view)
        }

        nextBtn.setOnClickListener {
            if (selectedChoice != null){
                evaluateSelectedChoice(selectedChoice!!)
                changeQuestion(view)
                onSelectedViewStyle(ans1, ans2, ans3, ans4, view,true)
            } else Toast.makeText(view.context,"Please select your answer!",Toast.LENGTH_SHORT).show()
        }

        skipBtn.setOnClickListener {
            changeQuestion(view)
            onSelectedViewStyle(ans1, ans2, ans3, ans4, view,true)
        }

        //Answering buttons
        ans1.setOnClickListener {
            selectedChoice = currentQuiz.a
            onSelectedViewStyle(ans1, ans2, ans3, ans4, view,false)
        }
        ans2.setOnClickListener {
            selectedChoice = currentQuiz.b
            onSelectedViewStyle(ans2,ans1, ans3, ans4, view,false)
        }
        ans3.setOnClickListener {
            selectedChoice = currentQuiz.c
            onSelectedViewStyle( ans3,ans1,ans2, ans4, view,false)
        }
        ans4?.setOnClickListener {
            selectedChoice = currentQuiz.d
            onSelectedViewStyle(ans4,ans2,ans1, ans3, view,false)
        }

        Thread.sleep(600)
        return view
    }

    private fun changeQuestion(view:View) {
        var questionIdx = appViewModel?.getIndex()?: 0
        if(questionIdx > 0){
            val selectedAns = if(selectedChoice!=null) selectedChoice else ""
            appViewModel?.getAnswers()?.add(selectedAns!!)
        }

        //Go to results page
       if (questionIdx == 15) {
           answers = appViewModel?.getAnswers()!!
           var score =appViewModel?.getFinalScore()
           answers.add(score.toString())
           val action = QuizFragmentDirections.actionQuizFragmentToResultsFragment(
               answers.toTypedArray())
           Navigation.findNavController(view).navigate(action)
        }
        else{
           currentQuiz = questions[questionIdx]
           tvQuestion.text = currentQuiz.question
           ans1.text = "A. "+currentQuiz.a
           ans2.text = "B. "+currentQuiz.b
           ans3.text = "C. "+currentQuiz.c
           ans4.text = "D. "+currentQuiz.d
           progress.text = questionIdx.toString()+"/15"
           appViewModel?.updateIndex()
           questionIdx++
           counter.text = questionIdx.toString()+"."
           selectedChoice = null
        }

    }

    fun onSelectedViewStyle(btnClicked: Button, btn1: Button, btn2:Button, btn3:Button, it:View, clearView:Boolean){
        if(clearView){
            btnClicked.setBackgroundColor(Color.WHITE)
            btnClicked.setTextColor(Color.BLACK);
        }else{
            btnClicked.setBackgroundColor(ContextCompat.getColor(it.context, R.color.back))
            btnClicked.setTextColor(Color.WHITE);
        }
        btn1.setBackgroundColor(Color.WHITE)
        btn1.setTextColor(Color.BLACK);
        btn2.setBackgroundColor(Color.WHITE)
        btn2.setTextColor(Color.BLACK);
        btn3.setBackgroundColor(Color.WHITE)
        btn3.setTextColor(Color.BLACK);
    }

    private fun evaluateSelectedChoice(ans: String) {
        if (currentQuiz.answer == ans) {
            appViewModel!!.getCurrentScore()
        }
    }

}