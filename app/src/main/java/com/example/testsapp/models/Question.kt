package com.example.testsapp.models

data class Question(
    var id: Int = 0,
    var title: String = "",
    var question_type: String = "",
    var answers: MutableList<String> = mutableListOf(),
    var right_answer_ids: MutableList<Int> = mutableListOf(),
    var picture_url: String = ""
)
