package com.example.testsapp.ui.composables.pages.settings

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun AccountPage(navController: NavHostController, mainViewModel: MainViewModel){
    val currentUser = mainViewModel.currentUser.value
    val context = LocalContext.current
    val name = remember {
        mutableStateOf(mainViewModel.currentUser.value.name)
    }
    val login = remember {
        mutableStateOf(mainViewModel.currentUser.value.login)
    }
    val email = remember {
        mutableStateOf(mainViewModel.currentUser.value.email)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.align(Alignment.TopCenter)) {

            Header(navController = navController, title = "Аккаунт")

            Box(modifier = Modifier
                .fillMaxHeight(0.33f)) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .clip(RoundedCornerShape(15.dp))
                        .align(Alignment.TopCenter)
                ) {
                    Image(painter = painterResource(id = R.drawable.background),
                        contentDescription = "Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize())
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Image(
                        modifier = Modifier
                            .size(size = 100.dp)
                            .clip(shape = CircleShape)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Profile Image")
                }

            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(currentUser.login,
                fontSize = 20.sp,
                style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Имя") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.main_orange),
                    cursorColor = colorResource(id = R.color.main_orange),
                    trailingIconColor = colorResource(id = R.color.main_orange),
                    focusedBorderColor = colorResource(id = R.color.main_orange),
                    focusedLabelColor = colorResource(id = R.color.main_orange)
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = login.value,
                onValueChange = { login.value = it },
                label = { Text("Логин") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.main_orange),
                    cursorColor = colorResource(id = R.color.main_orange),
                    leadingIconColor = colorResource(id = R.color.main_orange),
                    focusedBorderColor = colorResource(id = R.color.main_orange),
                    focusedLabelColor = colorResource(id = R.color.main_orange)
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Электронная почта") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.main_orange),
                    cursorColor = colorResource(id = R.color.main_orange),
                    leadingIconColor = colorResource(id = R.color.main_orange),
                    focusedBorderColor = colorResource(id = R.color.main_orange),
                    focusedLabelColor = colorResource(id = R.color.main_orange)
                )
            )

        }
        Button(onClick = {
            if (name.value != currentUser.name) {
                currentUser.name = name.value
            }
            if (login.value != currentUser.login) {
                currentUser.login = login.value
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(login.value).build()
                SingletoneFirebase.instance.auth.currentUser?.updateProfile(profileUpdates)
            }
            if (email.value != currentUser.email) {
                currentUser.email = email.value
                SingletoneFirebase.instance.auth.currentUser?.updateEmail(email.value)
            }
            SingletoneFirebase.instance.database.getReference("Users").child(currentUser.id).setValue(
                currentUser
            )
            mainViewModel.currentUser.value = currentUser
            Toast.makeText(context, "Изменения сохранены!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
            shape = CircleShape,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)) {
            Text(
                "Сохранить",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}


