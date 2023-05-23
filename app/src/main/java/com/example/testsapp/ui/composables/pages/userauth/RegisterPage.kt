package com.example.testsapp.ui.composables.pages.userauth

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.testsapp.models.User
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RegisterPage(navController: NavHostController,
               scope: CoroutineScope,
               drawerState: DrawerState,
                 mainViewModel: MainViewModel
){
    scope.launch {
        drawerState.close()
    }
    val context = LocalContext.current
    val mAuth = SingletoneFirebase.instance.auth
    val database = SingletoneFirebase.instance.database
    val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    val email = remember {
        mutableStateOf("")
    }
    val name = remember{
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val passwordAgain = remember {
        mutableStateOf("")
    }
    val isErrorName = remember {
        mutableStateOf(false)
    }
    val isErrorEmail = remember {
        mutableStateOf(false)
    }
    val isErrorPass = remember {
        mutableStateOf(false)
    }
    val isErrorPassAgain = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Header(navController = navController, title = "Регистрация")
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Создать Новый Аккаунт",
                    fontSize = 40.sp,
                    style = MaterialTheme.typography.h1
                )
                Text("Создайте аккаунт, чтобы сохранять свои результаты и созданные тесты",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.light_gray)
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    value = name.value,
                    onValueChange = {
                        isErrorName.value = false
                        name.value = it
                    },
                    placeholder = {
                        Text("Полное имя",
                            style = MaterialTheme.typography.body1,
                            color = colorResource(id = R.color.light_gray)
                        )},
                    isError = isErrorName.value,
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.main_orange),
                        unfocusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    value = passwordAgain.value,
                    onValueChange = {
                        isErrorPassAgain.value = false
                        passwordAgain.value = it
                    },
                    placeholder = {
                        Text("Подтверждение пароля",
                            style = MaterialTheme.typography.body1,
                            color = colorResource(id = R.color.light_gray)
                        )},
                    isError = isErrorPassAgain.value,
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

        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row (horizontalArrangement = Arrangement.Center){
                Text("Уже есть аккаунт? ",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body1
                )
                Text("Авторизуйтесь",
                    modifier = Modifier.clickable {
                        navController.navigate("SignInPage")
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.h1,
                    color = colorResource(id = R.color.main_orange)
                )
            }
            Button(onClick = {
                if (name.value != "" && email.value != "" && password.value != "" && passwordAgain.value != ""){
                    if (password.value != passwordAgain.value){
                        isErrorPass.value = true
                        isErrorPassAgain.value = true
                        Toast.makeText(context, "Пароли не совпадают!", Toast.LENGTH_SHORT).show()
                    } else{
                        if (password.value.length < 6){
                            isErrorPass.value = true
                            Toast.makeText(context, "Длина пароля должна быть не менее 6 символов!", Toast.LENGTH_SHORT).show()
                        } else{
                            mAuth.createUserWithEmailAndPassword(email.value, password.value).addOnCompleteListener{
                                if (it.isSuccessful){
                                    val user = User(email = email.value, name = name.value, login = name.value, password = password.value, role = "Пользователь")
                                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name.value).build()
                                    mAuth.currentUser?.updateProfile(profileUpdates)
                                    user.id = mAuth.currentUser?.uid.toString()
                                    mAuth.currentUser?.let { it1 ->
                                        database.getReference("Users")
                                            .child(it1.uid)
                                            .setValue(user).addOnCompleteListener {
                                                if (it.isSuccessful){
                                                    Toast.makeText(context, "Регистрация прошла успешно!", Toast.LENGTH_LONG).show()
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

                                                            } else{
                                                            Toast.makeText(context, "Ошибка авторизации!", Toast.LENGTH_LONG).show()
                                                        }
                                                    }

                                                } else{
                                                    Toast.makeText(context, "Ошибка регистрации!", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                    }
                                }else{
                                    Toast.makeText(context, "Ошибка регистрации!", Toast.LENGTH_LONG).show()
                                }
                            }
                            //
                            navController.navigate("HomePage")
                        }
                    }


                    // если все ок, авторизация с фаербейс

                } else {
                    Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_LONG).show()
                    if (name.value == ""){
                        isErrorName.value = true
                    }
                    if (email.value == ""){
                        isErrorEmail.value = true
                    }
                    if (password.value == ""){
                        isErrorPass.value = true
                    }
                    if (passwordAgain.value == ""){
                        isErrorPassAgain.value = true
                    }
                } },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                Text("Зарегистрироваться",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }

    }
}