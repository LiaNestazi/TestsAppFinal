package com.example.testsapp.singletone

import com.example.testsapp.models.*

class SingletoneTypes private constructor() {
    var created_test = Test()
    var questions = mutableListOf<Question>()
    var current_question = Question()
    companion object {
        val instance = SingletoneTypes()
    }
}
