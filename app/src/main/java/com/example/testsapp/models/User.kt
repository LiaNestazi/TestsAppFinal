package com.example.testsapp.models

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var login: String = "",
    var password: String = "",
    var role: String = "",
    var results: MutableList<Result> = mutableListOf()
)
