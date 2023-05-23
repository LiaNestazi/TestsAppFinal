package com.example.testsapp.ui.composables.pages.playtest

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.Result
import com.example.testsapp.models.Test
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.RatingBar
import com.example.testsapp.viewmodels.MainViewModel
import kotlin.math.roundToInt

@Composable
fun ResultsPage(navController: NavHostController, mainViewModel: MainViewModel, item_id: String?) {
    val test = remember {
        mutableStateOf(Test())
    }
    val questions = remember {
        mutableStateOf(test.value.questions)
    }
    if (item_id != null) {
        SingletoneFirebase.instance.database.getReference("Tests").child(item_id).get().addOnSuccessListener {
            test.value = it.getValue(Test::class.java) as Test
            questions.value = test.value.questions
        }
    }
    val label = remember {
        mutableStateOf("Ваш результат:")
    }
    var result = 0
    val selectedAnswersCopy = mainViewModel.selectedAnswersIndexes
    while (selectedAnswersCopy.size<test.value.questions.size){
        selectedAnswersCopy.add(-1)
    }
    selectedAnswersCopy.forEachIndexed { index, answer ->
        if (index<questions.value.size) {
            if (answer != -1){
                if (questions.value[index].right_answer_ids.contains(answer+1)){
                    result++
                }
            }
        }
    }
    val openRatingDialog = remember{
        mutableStateOf(false)
    }
    var newRating = 0
    val context = LocalContext.current

    when (item_id){
        null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Тест временно недоступен",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body1
                )
            }
        }
        else -> {
            when (test.value.name){
                "" ->{
                    CircularProgressIndicator(color = colorResource(id = R.color.main_orange))
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize()
                    ) {
                        Column {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 65.dp)
                            ) {
                                Text(
                                    label.value,
                                    fontSize = 40.sp,
                                    style = MaterialTheme.typography.h1
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                LazyColumn(content = {
                                    itemsIndexed(selectedAnswersCopy){ question_num, answer_num ->
                                        if (question_num<questions.value.size){
                                            val answers = questions.value[question_num].answers
                                            val rightAnswerId = questions.value[question_num].right_answer_ids[0]
                                            var chosenAnswer = "Не выбран"
                                            val rightAnswer = answers[rightAnswerId-1]
                                            if (answer_num !=-1){
                                                chosenAnswer = answers[answer_num]
                                            }
                                            var isRight = "неверно"
                                            if (questions.value[question_num].right_answer_ids.contains(answer_num+1)){
                                                isRight = "верно"
                                            }
                                            Text(text = "Вопрос ${question_num+1} - $isRight",
                                                fontSize = 16.sp,
                                                style = MaterialTheme.typography.h1
                                            )
                                            Spacer(modifier = Modifier.size(4.dp))
                                            Text(text = "Выбранный ответ: $chosenAnswer",
                                                fontSize = 14.sp,
                                                style = MaterialTheme.typography.body2,
                                                color = colorResource(id = R.color.light_gray)
                                            )
                                            Text(text = "Правильный ответ: $rightAnswer",
                                                fontSize = 14.sp,
                                                style = MaterialTheme.typography.body2,
                                                color = colorResource(id = R.color.light_gray)
                                            )
                                            Spacer(modifier = Modifier.size(4.dp))
                                            Log.d("MyTag", "$question_num $answer_num да $isRight")
                                        } else{
                                            Log.d("MyTag", "$question_num $answer_num нет")
                                        }

                                    }
                                })
                                Spacer(modifier = Modifier.size(16.dp))
                                Text(
                                    "Итого: $result/${test.value.question_count}",
                                    fontSize = 24.sp,
                                    style = MaterialTheme.typography.h1
                                )
                            }
                        }
                        Button(
                            onClick = {
                                mainViewModel.selectedQuestion.value = 1
                                mainViewModel.selectedAnswersIndexes = MutableList(1){-1}
                                var contains = false
                                var lastResult = 0
                                var lastResultIndex = 0
                                val userResults = mainViewModel.currentUser.value.results

                                if (SingletoneFirebase.instance.auth.currentUser != null){
                                    userResults.forEachIndexed { index, result ->
                                        if (result.test_uid == item_id){
                                            contains = true
                                            lastResult = result.score
                                            lastResultIndex = index
                                        }
                                    }
                                    if (!contains){
                                        mainViewModel.currentUser.value.results.add(Result(item_id,result))
                                        SingletoneFirebase.instance.database.getReference("Users")
                                            .child(mainViewModel.currentUser.value.id)
                                            .setValue(mainViewModel.currentUser.value)
                                    } else{
                                        if (lastResult<result){
                                            mainViewModel.currentUser.value.results[lastResultIndex] = Result(item_id,result)
                                            SingletoneFirebase.instance.database.getReference("Users")
                                                .child(mainViewModel.currentUser.value.id)
                                                .setValue(mainViewModel.currentUser.value)
                                        }
                                    }
                                }
                                openRatingDialog.value = true
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
                                "На домашнюю страницу",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }

                    }




                    //Оценка теста
                    if (openRatingDialog.value){
                        AlertDialog(
                            onDismissRequest = {
                                navController.navigate("HomePage")
                                openRatingDialog.value = false
                            },
                            title = {
                                Text(
                                    "Оцените тест",
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.h1,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                            },
                            text = {
                                newRating = RatingBar(rating = 0, changeable = true)
                            },
                            buttons = {
                                Row {
                                    Button(
                                        onClick = {
                                            navController.navigate("HomePage")
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = colorResource(id = R.color.gray_background),
                                            contentColor = Color.White
                                        ),
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp)
                                    ) {
                                        Text(
                                            "Нет, спасибо",
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp,
                                            style = MaterialTheme.typography.h1
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            changeTestRating(item_id, test.value, newRating)
                                            Toast.makeText(context, "Оценка учтена!", Toast.LENGTH_SHORT).show()
                                            navController.navigate("HomePage")
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = colorResource(id = R.color.main_orange),
                                            contentColor = Color.White
                                        ),
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp)
                                    ) {
                                        Text(
                                            "Оценить",
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp,
                                            style = MaterialTheme.typography.h1
                                        )
                                    }
                                }
                            }
                        )
                    }

                    //
                }
            }
        }
    }

}

fun changeTestRating(itemId: String, currentTest: Test, newRating: Int) {
    currentTest.rating = ((currentTest.rating + newRating) / 2.0).roundToInt()
    SingletoneFirebase.instance.database.getReference("Tests").child(itemId).setValue(currentTest)

}
