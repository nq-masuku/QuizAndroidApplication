package com.quizapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation


class ResultsFragment : Fragment() {
    private lateinit var yourScore: TextView
    private lateinit var correctA: TextView
    private lateinit var wrongA: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results, container, false)
        var button = view?.findViewById<Button>(R.id.retry)
        var button2 = view?.findViewById<Button>(R.id.analyses)
        correctA = view.findViewById(R.id.correctA)
        wrongA = view.findViewById(R.id.wrongA)
        yourScore = view.findViewById(R.id.score)

        val answers = ResultsFragmentArgs.fromBundle(requireArguments()).answers
        val score = answers[answers.size-1]
        val wrong = answers.size - (score.toInt()+1)
        correctA.text = correctA.text.toString() + score
        wrongA.text = wrongA.text.toString() + wrong
        yourScore.text = yourScore.text.toString() + score + "/15"

        button?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_resultsFragment_to_quizFragment)
        }
        button2?.setOnClickListener {
            val action = ResultsFragmentDirections.actionResultsFragmentToResultsAnalysisFragment(
                answers
            )
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }

}