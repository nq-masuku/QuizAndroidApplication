package com.quizapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.quizapplication.db.dao.AppDatabase
import com.quizapplication.db.model.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ResultsAnalysisFragment : Fragment() {
    private lateinit var appDb : AppDatabase
    private lateinit var questions: List<Quiz>
    private lateinit var viewL: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results_analysis, container, false)
        val answers = ResultsAnalysisFragmentArgs.fromBundle(requireArguments()).answers
        appDb = AppDatabase.getDatabase(view.context)
        viewL = view.findViewById(R.id.layout)

        GlobalScope.launch(Dispatchers.IO) {
            questions = appDb.quizDao().getQuizQuestions()

            questions.forEach{ q ->
                q.answer
            }

            updateView(view,questions, answers.toList())
        }

        return view
    }

    private fun updateView(v:View, questions: List<Quiz>, answers: List<String>){
        questions.forEachIndexed { index, quiz ->

            getActivity()?.runOnUiThread(Runnable {
                var tView= TextView(v.context);
                val listItem = String.format("%s\nYour answer: %s\nCorrect answer: %s",quiz.question,answers[index], quiz.answer + "\n")
                tView.text = listItem
                tView.setTextSize(resources.getDimension(R.dimen.textsize1))
                tView.setTextColor(Color.WHITE)
                viewL.addView(tView);
            })

        }
    }

}