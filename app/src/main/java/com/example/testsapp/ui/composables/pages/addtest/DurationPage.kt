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
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.singletone.SingletoneTypes
import com.example.testsapp.ui.composables.functions.custom.Header

@Composable
fun DurationPage(navController: NavHostController){
    SingletoneTypes.instance.questions = mutableListOf()
    val context = LocalContext.current
    val hour = remember{
        mutableStateOf("")
    }
    val minutes = remember{
        mutableStateOf("")
    }
    val seconds = remember{
        mutableStateOf("")
    }
    val label = remember {
        mutableStateOf("Задайте длительность викторины")
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
                Text("Часы",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = hour.value,
                    onValueChange = {
                        hour.value = it
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
                Spacer(modifier = Modifier.size(8.dp))

                Text("Минуты",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = minutes.value,
                    onValueChange = {
                        minutes.value = it
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
                Spacer(modifier = Modifier.size(8.dp))

                Text("Секунды",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = seconds.value,
                    onValueChange = {
                        seconds.value = it
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
            var hourInt = 0
            var minutesInt = 0
            var secondsInt = 0
            if (hour.value != "") hourInt = hour.value.toInt()
            if (minutes.value != "") minutesInt = minutes.value.toInt()
            if (seconds.value != "") secondsInt = seconds.value.toInt()
            if (hourInt != 0 || minutesInt != 0 || secondsInt != 0){
                SingletoneTypes.instance.created_test.timeInMillis = hourInt*3600000L+minutesInt*60000L+secondsInt*1000L
                //Добавление созданного теста в БД
                SingletoneFirebase.instance.addTestToDB(SingletoneTypes.instance.created_test)
                navController.navigate("SuccessPage")
            } else {
                Toast.makeText(context, "Необходимо ввести время!", Toast.LENGTH_SHORT).show()
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