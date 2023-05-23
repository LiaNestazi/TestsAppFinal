package com.example.testsapp.ui.composables.pages.addtest

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.Question
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.singletone.SingletoneTypes
import com.example.testsapp.ui.composables.functions.custom.Header

@Composable
fun NQuestionContentPage(currentN: Int, navController: NavHostController) {
    var currentQuestion = Question()

    if (SingletoneTypes.instance.current_question.question_type == "Один ответ"){
        var firstNum =  0
        if (SingletoneTypes.instance.current_question.right_answer_ids.isNotEmpty()){
            firstNum = SingletoneTypes.instance.current_question.right_answer_ids[0]
        }
        SingletoneTypes.instance.current_question.right_answer_ids.clear()
        if (firstNum != 0){
            SingletoneTypes.instance.current_question.right_answer_ids.add(firstNum)
        }
    }


    if (SingletoneTypes.instance.questions.isNotEmpty()){
        if (SingletoneTypes.instance.questions.size > currentN-1) {
            currentQuestion = SingletoneTypes.instance.questions[currentN-1]
        }
    }
    val count = SingletoneTypes.instance.created_test.question_count
    val label = remember {
        mutableStateOf("Вопрос $currentN/$count")
    }
    val title = remember {
        mutableStateOf(currentQuestion.title)
    }
    val var1 = remember {
        if (currentQuestion.answers.isEmpty()){
            mutableStateOf("")
        } else{
            mutableStateOf(currentQuestion.answers[0])
        }
    }
    val var2 = remember {
        if (currentQuestion.answers.isEmpty()){
            mutableStateOf("")
        } else{
            mutableStateOf(currentQuestion.answers[1])
        }
    }
    val var3 = remember {
        if (currentQuestion.answers.isEmpty()){
            mutableStateOf("")
        } else{
            mutableStateOf(currentQuestion.answers[2])
        }
    }
    val var4 = remember {
        if (currentQuestion.answers.isEmpty()){
            mutableStateOf("")
        } else{
            mutableStateOf(currentQuestion.answers[3])
        }
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val state1 = remember {
        if (SingletoneTypes.instance.current_question.right_answer_ids.contains(1)){
            mutableStateOf(true)
        } else{
            mutableStateOf(false)
        }
    }
    val state2 = remember {
        if (SingletoneTypes.instance.current_question.right_answer_ids.contains(2)){
            mutableStateOf(true)
        } else{
            mutableStateOf(false)
        }
    }
    val state3 = remember {
        if (SingletoneTypes.instance.current_question.right_answer_ids.contains(3)){
            mutableStateOf(true)
        } else{
            mutableStateOf(false)
        }
    }
    val state4 = remember {
        if (SingletoneTypes.instance.current_question.right_answer_ids.contains(4)){
            mutableStateOf(true)
        } else{
            mutableStateOf(false)
        }
    }




    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
        {
            Header(navController = navController, title = "Создание теста")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    label.value,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    "Заголовок",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = title.value,
                    onValueChange = {
                        title.value = it
                    },
                    isError = isError.value,
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    "Вариант 1",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1
                )
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        value = var1.value,
                        onValueChange = {
                            var1.value = it
                        },
                        isError = isError.value,
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colorResource(id = R.color.main_orange),
                            cursorColor = colorResource(id = R.color.main_orange),
                            trailingIconColor = colorResource(id = R.color.main_orange),
                            focusedBorderColor = colorResource(id = R.color.light_gray)
                        )
                    )
                    Checkbox(checked = state1.value,
                        onCheckedChange = {
                            state1.value = it
                            if (it){
                                if (SingletoneTypes.instance.current_question.question_type == "Один ответ"){
                                    SingletoneTypes.instance.current_question.right_answer_ids.clear()
                                    state2.value = false
                                    state3.value = false
                                    state4.value = false

                                }
                                chooseVariant(1)
                            } else{
                                deleteVariant(1)
                            }
                        },
                    colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.main_orange))
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    "Вариант 2",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1
                )
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        value = var2.value,
                        onValueChange = {
                            var2.value = it
                        },
                        isError = isError.value,
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colorResource(id = R.color.main_orange),
                            cursorColor = colorResource(id = R.color.main_orange),
                            trailingIconColor = colorResource(id = R.color.main_orange),
                            focusedBorderColor = colorResource(id = R.color.light_gray)
                        )
                    )
                    Checkbox(checked = state2.value,
                        onCheckedChange = {
                            state2.value = it
                            if (it){
                                if (SingletoneTypes.instance.current_question.question_type == "Один ответ"){
                                    SingletoneTypes.instance.current_question.right_answer_ids.clear()
                                    state1.value = false
                                    state3.value = false
                                    state4.value = false
                                }
                                chooseVariant(2)
                            } else{
                                deleteVariant(2)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.main_orange))
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    "Вариант 3",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1
                )
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        value = var3.value,
                        onValueChange = {
                            var3.value = it
                        },
                        isError = isError.value,
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colorResource(id = R.color.main_orange),
                            cursorColor = colorResource(id = R.color.main_orange),
                            trailingIconColor = colorResource(id = R.color.main_orange),
                            focusedBorderColor = colorResource(id = R.color.light_gray)
                        )
                    )
                    Checkbox(checked = state3.value,
                        onCheckedChange = {
                            state3.value = it
                            if (it){
                                if (SingletoneTypes.instance.current_question.question_type == "Один ответ"){
                                    SingletoneTypes.instance.current_question.right_answer_ids.clear()
                                    state1.value = false
                                    state2.value = false
                                    state4.value = false
                                }
                                chooseVariant(3)
                            } else{
                                deleteVariant(3)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.main_orange))
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    "Вариант 4",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1
                )
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        value = var4.value,
                        onValueChange = {
                            var4.value = it
                        },
                        isError = isError.value,
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colorResource(id = R.color.main_orange),
                            cursorColor = colorResource(id = R.color.main_orange),
                            trailingIconColor = colorResource(id = R.color.main_orange),
                            focusedBorderColor = colorResource(id = R.color.light_gray)
                        )
                    )
                    Checkbox(checked = state4.value,
                        onCheckedChange = {
                            state4.value = it
                            if (it){
                                if (SingletoneTypes.instance.current_question.question_type == "Один ответ"){
                                    SingletoneTypes.instance.current_question.right_answer_ids.clear()
                                    state1.value = false
                                    state2.value = false
                                    state3.value = false
                                }
                                chooseVariant(4)
                            } else{
                                deleteVariant(4)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.main_orange))
                    )
                }
            }
        }

        Button(
            onClick = {
                if (title.value == "" ||
                    var1.value == "" ||
                    var2.value == "" ||
                    var3.value == "" ||
                    var4.value == "" || (!state1.value && !state2.value && !state3.value && !state4.value)
                ){
                    isError.value = true
                } else{
                    SingletoneTypes.instance.current_question.title = title.value
                    SingletoneTypes.instance.current_question.answers = mutableListOf(var1.value, var2.value, var3.value, var4.value)



                    if (SingletoneTypes.instance.questions.size <= currentN-1){
                        SingletoneTypes.instance.questions.add(SingletoneTypes.instance.current_question)
                    }

                    Log.d("MyTag", SingletoneTypes.instance.questions.toString())

                    SingletoneTypes.instance.current_question = Question()

                    if (currentN < count){
                        navController.navigate("NQuestionTypePage/" + (currentN+1))
                    } else{
                        SingletoneTypes.instance.created_test.questions = SingletoneTypes.instance.questions
                        if (SingletoneTypes.instance.created_test.type == "Викторина"){
                            navController.navigate("DurationPage")
                        } else{
                            //Добавление созданного теста в БД
                            SingletoneFirebase.instance.addTestToDB(SingletoneTypes.instance.created_test)
                            navController.navigate("SuccessPage")
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.main_orange),
                contentColor = Color.White
            ),
            shape = CircleShape,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                "Продолжить",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

fun chooseVariant(varNum: Int){
    SingletoneTypes.instance.current_question.right_answer_ids.add(varNum)
    Log.d("MyTag", SingletoneTypes.instance.current_question.right_answer_ids.toString())
}

fun deleteVariant(varNum: Int){
    SingletoneTypes.instance.current_question.right_answer_ids.remove(varNum)
    Log.d("MyTag", SingletoneTypes.instance.current_question.right_answer_ids.toString())
}