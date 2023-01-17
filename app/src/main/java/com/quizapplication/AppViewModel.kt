package com.quizapplication
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    private  var score = 0
    private var questionIdx = 0
    private var scoreLiveData= MutableLiveData<Int>()
    private var answers: MutableList<String> = mutableListOf()

    fun getInitialScore(): MutableLiveData<Int> {
        scoreLiveData.value = score
        return  scoreLiveData
    }
    fun getCurrentScore(){
        score+=1
        scoreLiveData.value= score
    }
    fun getFinalScore():Int{
        return score
    }

    fun getIndex(): Int{
        return questionIdx
    }

    fun updateIndex(){
        questionIdx++
    }

    fun getAnswers(): MutableList<String>{
        return answers
    }


}
