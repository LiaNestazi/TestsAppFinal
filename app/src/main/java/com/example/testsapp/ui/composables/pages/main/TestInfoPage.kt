package com.example.testsapp.ui.composables.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.testsapp.models.Test
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.RatingBar
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel

@Composable
fun TestInfoPage(navController: NavHostController, mainViewModel: MainViewModel, item_id: String?){
    Box(modifier = Modifier.fillMaxSize()) {
        val test = remember {
            mutableStateOf(Test())
        }

        if (item_id != null) {
            SingletoneFirebase.instance.database.getReference("Tests").child(item_id).get().addOnSuccessListener {
                test.value = it.getValue(Test::class.java) as Test
            }
        }

        when(test.value.name){
            "" ->{
                CircularProgressIndicator(color = colorResource(id = R.color.main_orange))
            }
            else ->{
                val author = remember {
                    if (test.value.author_login == "")
                        mutableStateOf("Без автора")
                    else
                        mutableStateOf(test.value.author_login)
                }
                val results = mainViewModel.currentUser.value.results
                var score = -1
                for (result in results){
                    if (result.test_uid == item_id){
                        score = result.score
                    }
                }
                val result = remember {
                    if (score != -1){
                        mutableStateOf(score.toString())
                    } else{
                        mutableStateOf("Не пройдено")
                    }
                }
                Column(modifier = Modifier.align(Alignment.TopCenter)) {
                    Header(navController = navController, title = "Информация о тесте")
                    Column(modifier = Modifier
                        .padding(horizontal = 16.dp)) {
                        Text(text = test.value.name,
                            fontSize = 40.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(text = test.value.description,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(top = 8.dp),
                            color = colorResource(id = R.color.light_gray)
                        )
                        Row {
                            Text(text = "Количество вопросов: ",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text(text = test.value.question_count.toString(),
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(top = 50.dp)) {
                            Text(text = "Лучший результат: ",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.h1
                            )
                            Text(text = result.value,
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
                Box(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)){
                    Row(modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxHeight(0.35f)
                        .fillMaxWidth(0.5f),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Рейтинг: ",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        RatingBar(rating = test.value.rating)
                    }
                    Row(modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth(0.5f),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                        Text(text = "Автор: ",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(text = author.value,
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Button(onClick = { navController.navigate("PlayTestPage/$item_id") },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)) {
                        Text("Пройти тест",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }

    }
}