package com.example.testsapp.ui.composables.pages.userauth

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SignInPage(navController: NavHostController,
               scope: CoroutineScope,
               drawerState: DrawerState,
               mainViewModel: MainViewModel
){
    scope.launch {
        drawerState.close()
    }
    val email = remember{
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val isErrorEmail = remember {
        mutableStateOf(false)
    }
    val isErrorPass = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val mAuth = SingletoneFirebase.instance.auth
    val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Header(navController = navController, title = "Авторизация")
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Добро Пожаловать!",
                    fontSize = 40.sp,
                    style = MaterialTheme.typography.h1
                )
                Text("Авторизуйтесь, чтобы сохранять свои результаты и созданные тесты",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.light_gray)
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    value = email.value,
                    onValueChange = {
                        isErrorEmail.value = false
                        email.value = it
                    },
                    placeholder = {
                        Text("Электронная почта",
                        style = MaterialTheme.typography.body1,
                        color = colorResource(id = R.color.light_gray)
                    )},
                    isError = isErrorEmail.value,
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.main_orange),
                        unfocusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password.value,
                    onValueChange = {
                        isErrorPass.value = false
                        password.value = it
                    },
                    placeholder = {
                        Text("Пароль",
                            style = MaterialTheme.typography.body1,
                            color = colorResource(id = R.color.light_gray)
                        )},
                    isError = isErrorPass.value,
                    shape = RoundedCornerShape(14.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.main_orange),
                        unfocusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
            }
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Row (horizontalArrangement = Arrangement.Center){
                Text("Еще нет аккаунта? ",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body1
                )
                Text("Создайте аккаунт",
                    modifier = Modifier.clickable {
                        navController.navigate("RegisterPage")
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.h1,
                    color = colorResource(id = R.color.main_orange)
                )
            }
            Button(onClick = {
                if (email.value != "" && password.value != ""){
                    if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                        Toast.makeText(context, "Введите корректный адрес электронной почты!", Toast.LENGTH_LONG).show()
                        isErrorEmail.value = true
                        return@Button
                    }
                    if (password.value.length < 6) {
                        Toast.makeText(context, "Длина пароля должна быть не меньше 6 символов!", Toast.LENGTH_LONG).show()
                        isErrorPass.value = true
                        return@Button
                    }
                    mAuth.signInWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                if (sharedPreferences != null) {
                                    with(sharedPreferences.edit()) {
                                        putString("email", email.value)
                                        putString("password", password.value)
                                        apply()
                                    }
                                }
                                mainViewModel.drawerAuthState.value = true
                                Toast.makeText(context, "Авторизация прошла успешно!", Toast.LENGTH_LONG).show()
                                navController.navigate("HomePage")

                            } else{
                                Toast.makeText(context, "Неверная почта или пароль!", Toast.LENGTH_LONG).show()
                            }
                        }

                } else {
                    Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_LONG).show()
                    if (email.value == "") {
                        isErrorEmail.value = true
                    }
                    if (password.value == "") {
                        isErrorPass.value = true
                    }
                } },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                Text("Авторизоваться",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }

    }
}