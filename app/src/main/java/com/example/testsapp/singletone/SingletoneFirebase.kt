package com.example.testsapp.singletone

import com.example.testsapp.models.Test
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SingletoneFirebase private constructor(){
    companion object {
        val instance = SingletoneFirebase()
    }
    val database = Firebase.database("https://testsapp-5092c-default-rtdb.europe-west1.firebasedatabase.app/")
    val auth = FirebaseAuth.getInstance()
    val testsReference = database.getReference("Tests")
    private var testsList = mutableListOf<Test>()

    fun addTestToDB(test: Test): Boolean{
        val id = testsReference.push().key
        if (id != null) {
            test.id = id
            testsReference.child(id).setValue(test)
            return true
        }
        return false
    }
}