package com.example.testsapp.models

data class Test(
    var id: String = "",
    var name: String ="",
    var description: String="",
    var type: String = "",
    var question_count: Int = 0,
    var rating: Int = 0,
    var timeInMillis: Long = 0L,
    var author_id: String = "",
    var author_login: String = "",
    var questions: List<Question> = mutableListOf()
)
