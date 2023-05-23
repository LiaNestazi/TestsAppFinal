package com.example.testsapp.ui.composables.pages.addtest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.example.testsapp.singletone.SingletoneTypes
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.ui.composables.functions.radiogroups.TypesRadioGroup

@Composable
fun NQuestionTypePage(currentN: Int, navController: NavHostController){
    val type = remember{
        mutableStateOf(SingletoneTypes.instance.current_question.question_type)
    }
    val count = SingletoneTypes.instance.created_test.question_count
    val label = remember {
        mutableStateOf("Вопрос $currentN/$count")
    }
    val isError = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Header(navController = navController, title = "Создание теста")
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(label.value,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1)
                Spacer(modifier = Modifier.size(8.dp))
                Text("Тип вопроса",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.size(4.dp))
                val titles = listOf("Один ответ", "Множественный ответ")
                val descriptions = listOf("Можно выбрать только один вариант из четырех предложенных", "Можно выбрать несколько вариантов из четырех предложенных")
                type.value = TypesRadioGroup(type.value, isError, titles = titles, descriptions = descriptions)
            }
        }
        Button(onClick = {
            if (type.value != ""){
                SingletoneTypes.instance.current_question.id = currentN
                SingletoneTypes.instance.current_question.question_type = type.value
                navController.navigate("NQuestionContentPage/$currentN")
            } else {
                isError.value = true
            } },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
            shape = CircleShape,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)) {
            Text("Продолжить",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}