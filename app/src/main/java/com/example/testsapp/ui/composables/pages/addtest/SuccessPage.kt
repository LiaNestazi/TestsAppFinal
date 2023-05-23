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
import com.example.testsapp.models.Test

@Composable
fun SuccessPage(navController: NavHostController) {
    val label = remember {
        mutableStateOf("Тест успешно создан!")
    }
    val testId = SingletoneTypes.instance.created_test.id
    val text = "Вы можете найти его в разделе \"Мои тесты\" в личном кабинете или в поиске по названию или айди: $testId"
    Box(modifier = Modifier.fillMaxSize()) {
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
                Text(
                    text,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body2,
                    color = colorResource(id = R.color.light_gray)
                )
            }
        }
        Button(
            onClick = {
                // ЛОГИКА СОХРАНЕНИЯ ТЕСТА В БД
                SingletoneTypes.instance.created_test = Test()
                navController.navigate("HomePage")
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
}