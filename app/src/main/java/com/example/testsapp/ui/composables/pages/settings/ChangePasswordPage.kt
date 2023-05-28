package com.example.testsapp.ui.composables.pages.settings

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel

@Composable
fun ChangePasswordPage(navController: NavHostController, mainViewModel: MainViewModel){
    val currentUser = mainViewModel.currentUser.value
    val oldPassword = remember{
        mutableStateOf("")
    }
    val newPassword = remember {
        mutableStateOf("")
    }
    val newPasswordAgain = remember {
        mutableStateOf("")
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Header(navController = navController, title = "Сменить пароль")
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text("Введите старый пароль",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1)
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = oldPassword.value,
                    onValueChange = {
                        oldPassword.value = it
                    },
                    isError = isError.value,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Введите новый пароль",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1)
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = newPassword.value,
                    onValueChange = {
                        newPassword.value = it
                    },
                    isError = isError.value,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Введите новый пароль еще раз",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1)
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = newPasswordAgain.value,
                    onValueChange = {
                        newPasswordAgain.value = it
                    },
                    isError = isError.value,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.main_orange),
                        cursorColor = colorResource(id = R.color.main_orange),
                        trailingIconColor = colorResource(id = R.color.main_orange),
                        focusedBorderColor = colorResource(id = R.color.light_gray)
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        Button(onClick = {
            if (newPassword.value != "" && oldPassword.value != "" && newPasswordAgain.value != ""){
                if (newPassword.value == newPasswordAgain.value){

                    if (!oldPassword.value.equals(currentUser.password)){
                        Toast.makeText(context,"Неверный пароль!", Toast.LENGTH_SHORT).show()
                    } else{
                        SingletoneFirebase.instance.auth.currentUser?.updatePassword(newPassword.value)
                        currentUser.password = newPassword.value
                        SingletoneFirebase.instance.database.getReference("Users").child(currentUser.id).setValue(
                            currentUser
                        )
                        mainViewModel.currentUser.value = currentUser
                        navController.popBackStack()
                        Toast.makeText(context,"Пароль успешно изменен!", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    isError.value = true
                }
            } else {
                isError.value = true
            } },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
            shape = CircleShape,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)) {
            Text("Сохранить",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}