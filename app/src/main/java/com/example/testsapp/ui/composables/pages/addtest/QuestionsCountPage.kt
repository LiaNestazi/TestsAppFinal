package com.example.testsapp.ui.composables.pages.addtest

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.singletone.SingletoneTypes
import com.example.testsapp.ui.composables.functions.custom.Header

@Composable
fun QuestionsCountPage(navController: NavHostController){
    val count = remember{
        if (SingletoneTypes.instance.created_test.question_count == 0){
            mutableStateOf("")
        } else{
            mutableStateOf(SingletoneTypes.instance.created_test.question_count.toString())
        }
    }
    val label = remember {
        mutableStateOf("Задайте количество вопросов")
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = count.value,
                    onValueChange = {
                        isError.value = false
                        count.value = it
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
            }
        }
        Button(onClick = {
            if (count.value != ""){
                if (!isNumeric(count.value)){
                    isError.value = true
                    Toast.makeText(context, "Введите число", Toast.LENGTH_SHORT).show()
                } else{
                    SingletoneTypes.instance.created_test.question_count = count.value.toInt()
                    navController.navigate("NQuestionTypePage/"+1)
                }
            } else {
                Toast.makeText(context, "Поле не может быть пустым", Toast.LENGTH_SHORT).show()
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

fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}