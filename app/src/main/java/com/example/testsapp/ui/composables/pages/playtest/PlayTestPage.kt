package com.example.testsapp.ui.composables.pages.playtest

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.Test
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.FAB
import com.example.testsapp.ui.composables.functions.radiogroups.AnswersRadioGroup
import com.example.testsapp.ui.composables.functions.radiogroups.QuestionsRadioGroup
import com.example.testsapp.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun PlayTestPage(navController: NavHostController, mainViewModel: MainViewModel, item_id: String?){
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
            Log.d("MyTag", test.toString())
        }
    }
    var isTimeRanOut: Boolean

    when (item_id){
        null->{
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
        else->{
            when (test.value.name){
                "" -> {
                    CircularProgressIndicator(color = colorResource(id = R.color.main_orange))
                }
                else -> {
                    val isPrevEnabled = remember {
                        if (test.value.type == "Тест"){
                            mutableStateOf(mainViewModel.selectedQuestion.value != 1)
                        } else{
                            mutableStateOf(false)
                        }
                    }
                    val nextText = remember {
                        if (mainViewModel.selectedQuestion.value == test.value.question_count){
                            mutableStateOf("Завершить тест")
                        } else{
                            mutableStateOf("Следующий вопрос")
                        }
                    }
                    val title = questions.value[mainViewModel.selectedQuestion.value-1].title
                    var forLog = -1
                    Box(modifier = Modifier
                        .fillMaxSize()) {
                        Column(modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(Alignment.TopCenter)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.13f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Box(
                                        contentAlignment = Alignment.CenterStart,
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .fillMaxWidth(0.14f)
                                    ) {
                                        FAB({
                                            mainViewModel.selectedQuestion.value = 1
                                            mainViewModel.selectedAnswersIndexes = MutableList(1){-1}
                                            navController.navigate("HomePage")
                                        }, iconResourceId = R.drawable.arrow_left)
                                    }
                                    Spacer(modifier = Modifier.size(4.dp))
                                    QuestionsRadioGroup(
                                        mainViewModel,
                                        questionCount = test.value.question_count
                                    )
                                }
                            }
                            Column(modifier = Modifier
                                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                if (test.value.type == "Викторина"){
                                    val time = test.value.timeInMillis
                                    isTimeRanOut = TimerCountdown(navController = navController, totalTime = time, item_id = item_id)
                                    LaunchedEffect(key1 = isTimeRanOut) {
                                        if (isTimeRanOut) {
                                            navController.navigate("ResultsPage/$item_id")
                                        }
                                    }
                                }
                                Text(
                                    title,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontSize = 40.sp,
                                    style = MaterialTheme.typography.h1
                                )
                                Box(modifier = Modifier
                                    .fillMaxHeight(0.35f)
                                    .fillMaxWidth()
                                ){
                                    Card(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        elevation = 5.dp,
                                        shape = RoundedCornerShape(15.dp)
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.background), contentDescription = "", contentScale = ContentScale.Crop)
                                    }
                                }
                                Spacer(modifier = Modifier.size(16.dp))

                                val questionNum = mainViewModel.selectedQuestion.value-1
                                var startValue = ""
                                if (mainViewModel.selectedAnswersIndexes[questionNum] != -1){
                                    startValue = questions.value[questionNum].answers[mainViewModel.selectedAnswersIndexes[questionNum]]
                                }
                                Log.d("MyTag", "start value $startValue")

                                forLog=AnswersRadioGroup(startValue, mainViewModel, questions.value)
                                mainViewModel.selectedAnswersIndexes[mainViewModel.selectedQuestion.value-1] = forLog
                            }
                        }

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.105f)
                            .align(Alignment.BottomCenter)
                            .padding(8.dp)){
                            Button(
                                onClick = {
                                    if (mainViewModel.selectedQuestion.value-1 > 0){
                                        mainViewModel.selectedQuestion.value = mainViewModel.selectedQuestion.value - 1
                                    }
                                    Log.d("MyTag","selected answers indexes"+" $forLog"+" ${mainViewModel.selectedAnswersIndexes}")
                                    nextText.value = "Следующий вопрос"
                                    isPrevEnabled.value = mainViewModel.selectedQuestion.value != 1
                                },
                                enabled = isPrevEnabled.value,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.light_gray),
                                    contentColor = Color.Black
                                ),
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f)
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterStart)
                            ) {
                                Text(
                                    "Предыдущий вопрос",
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.h1
                                )
                            }
                            Button(
                                onClick = {
                                    if(mainViewModel.selectedQuestion.value+1>mainViewModel.selectedAnswersIndexes.size){
                                        mainViewModel.selectedAnswersIndexes.add(-1)
                                    }
                                    if (nextText.value != "Завершить тест"){
                                        if (mainViewModel.selectedQuestion.value+1 < test.value.question_count){
                                            mainViewModel.selectedQuestion.value = mainViewModel.selectedQuestion.value + 1
                                            Log.d("MyTag","selected question"+mainViewModel.selectedQuestion.value)
                                            Log.d("MyTag","selected answers indexes"+" $forLog"+" ${mainViewModel.selectedAnswersIndexes}")
                                            nextText.value = "Следующий вопрос"
                                        }
                                        else{
                                            mainViewModel.selectedQuestion.value = mainViewModel.selectedQuestion.value + 1
                                            Log.d("MyTag","selected question"+mainViewModel.selectedQuestion.value)
                                            nextText.value = "Завершить тест"
                                        }
                                        if (test.value.type == "Тест"){
                                            isPrevEnabled.value = mainViewModel.selectedQuestion.value != 1
                                        }
                                    } else{
                                        navController.navigate("ResultsPage/$item_id")
                                        Log.d("MyTag","selected answers indexes"+" ${mainViewModel.selectedAnswersIndexes}")
                                    }

                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.main_orange),
                                    contentColor = Color.White
                                ),
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f)
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterEnd)
                            ) {
                                Text(
                                    nextText.value,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.h1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimerCountdown(
    navController: NavHostController,
    totalTime: Long,
    item_id: String?
): Boolean{
    val timer = remember {
        mutableStateOf(totalTime)
    }
    if (timer.value == 0L){
        return true
    }
    LaunchedEffect(key1 = timer.value) {
        if (timer.value > 0) {
            delay(1000L)
            timer.value -= 1000L
        }
    }
    val secMilSec: Long = 1000
    val minMilSec = 60 * secMilSec
    val hourMilSec = 60 * minMilSec
    val dayMilSec = 24 * hourMilSec
    val hours = (timer.value % dayMilSec / hourMilSec).toInt()
    val minutes = (timer.value % dayMilSec % hourMilSec / minMilSec).toInt()
    val seconds = (timer.value % dayMilSec % hourMilSec % minMilSec / secMilSec).toInt()

    Text(
        text = if (timer.value != 0L) "Оставшееся время: "+String.format("%02d:%02d:%02d", hours, minutes, seconds) else "Время закончилось",
        modifier = Modifier.clickable {
            if (timer.value == 0L){
                navController.navigate("ResultsPage/$item_id")
            }
        },
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        style = MaterialTheme.typography.body1,
        color = colorResource(id = R.color.main_orange)
    )
    return false
}
