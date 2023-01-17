package com.quizapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        var button = view?.findViewById<Button>(R.id.button)

        button?.setOnClickListener {
        val dialog = AlertDialog.Builder(view.context)
        dialog.setTitle("Quiz Instructions")
        dialog.setMessage("This Quiz consists of 15 questions, worth 1 point each. Note: you will have to restart it for any questions you skip.")

        dialog.setPositiveButton("Take Quiz Now") { dialogInterface, which ->
            Navigation.findNavController(view).navigate(R.id.home_to_quizFragment)
        }
        val builder: AlertDialog = dialog.create()
        builder.show()
        }


        var button2 = view?.findViewById<Button>(R.id.button2)
        button2?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.home_to_quizFragment)
        }

        return view
    }

}