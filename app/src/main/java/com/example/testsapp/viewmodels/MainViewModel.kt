package com.example.testsapp.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testsapp.models.Test
import com.example.testsapp.models.User
import com.example.testsapp.singletone.SingletoneFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainViewModel: ViewModel() {
    private val database = SingletoneFirebase.instance.database

    @SuppressLint("MutableCollectionMutableState")
    private var _tests = mutableStateOf<MutableList<Test>>(mutableListOf())
    val tests: State<MutableList<Test>> = _tests

    @SuppressLint("MutableCollectionMutableState")
    private var _users = mutableStateOf<MutableList<User>>(mutableListOf())
    val users: State<MutableList<User>> = _users

    val currentUser = mutableStateOf(User())
    val userLogin = mutableStateOf(currentUser.value.login)
    val userEmail = mutableStateOf(currentUser.value.email)

    val drawerAuthState = mutableStateOf(false)
    val isAdmin = mutableStateOf(false)
    val isModer = mutableStateOf(false)

    val selectedQuestion = mutableStateOf(1)
    var selectedAnswersIndexes = MutableList(1){-1}

    //SORTING
    val reversedList = mutableStateOf(false)
    val byNameList = mutableStateOf(false)
    val byRatingList = mutableStateOf(false)

    fun getTests(){
        val tempList = mutableStateListOf<Test>()
        database.getReference("Tests")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val test = DataSnap.getValue(Test::class.java)
                        if (test != null){
                            tempList.add(test)
                        }
                    }
                    if (reversedList.value){
                        _tests.value = tempList.asReversed()
                    }

                    if (byNameList.value){
                        tempList.sortBy { it.name }
                        _tests.value = tempList
                    }

                    if (byRatingList.value){
                        tempList.sortByDescending { it.rating }
                        _tests.value = tempList
                    }

                    if (!reversedList.value && !byNameList.value && !byRatingList.value){
                        _tests.value = tempList
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }
    fun getUsers(){
        val tempList = mutableStateListOf<User>()
        database.getReference("Users")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val user = DataSnap.getValue(User::class.java)
                        if (user != null){
                            tempList.add(user)
                        }
                    }
                    _users.value = tempList
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun sortTestsByNew(){
        byNameList.value = false
        byRatingList.value = false
        reversedList.value = !reversedList.value
    }
    fun sortTestsByName(){
        reversedList.value = false
        byRatingList.value = false
        byNameList.value = !byNameList.value
    }
    fun sortTestsByRating(){
        reversedList.value = false
        byNameList.value = false
        byRatingList.value = !byRatingList.value
    }
}