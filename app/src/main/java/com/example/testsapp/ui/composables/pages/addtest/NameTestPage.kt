package com.example.testsapp.ui.composables.pages.addtest

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
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.singletone.SingletoneTypes
import com.example.testsapp.ui.composables.functions.custom.Header

@Composable
fun NameTestPage(navController: NavHostController){
    SingletoneTypes.instance.questions = mutableListOf()
    val name = remember{
        mutableStateOf(SingletoneTypes.instance.created_test.name)
    }
    val label = remember {
        mutableStateOf("Введите название")
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name.value,
                    onValueChange = {
                        name.value = it
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
            if (name.value != ""){
                SingletoneTypes.instance.created_test.name = name.value
                val creatorId = SingletoneFirebase.instance.auth.currentUser?.uid
                val creatorLogin = SingletoneFirebase.instance.auth.currentUser?.displayName
                if (creatorId != null){
                    if (creatorLogin != null) {
                        SingletoneTypes.instance.created_test.author_login = creatorLogin
                    }
                    SingletoneTypes.instance.created_test.author_id = creatorId
                }
                navController.navigate("DescriptionPage")
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

