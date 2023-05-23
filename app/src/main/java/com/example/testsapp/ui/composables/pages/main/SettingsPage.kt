package com.example.testsapp.ui.composables.pages.main

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.NavigationDrawerItem
import com.example.testsapp.models.User
import com.example.testsapp.ui.composables.functions.custom.FAB
import com.example.testsapp.ui.composables.functions.custom.OptionsColumn
import com.example.testsapp.viewmodels.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(navController: NavHostController,
                 scope: CoroutineScope,
                 drawerState: DrawerState,
                 mainViewModel: MainViewModel
) {
    val sharedPreferences = LocalContext.current.getSharedPreferences("user", Context.MODE_PRIVATE)
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.13f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth(0.14f)){
                        FAB({ scope.launch { drawerState.open() } }, iconResourceId = R.drawable.category)
                    }
                    Box(contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth(0.55f)){
                        Text(
                            "Настройки",
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp, end = 3.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth(0.35f)
                    ){

                    }
                    Box(contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth(0.55f)
                    ){

                    }
                }
            }
            val accountList = prepareAccountList()

            Column {
                Text(
                    "Аккаунт",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
                )
                OptionsColumn(navController, list = accountList)
            }

        }


        Button(onClick = {
            Firebase.auth.signOut()
            if (sharedPreferences != null) {
                with(sharedPreferences.edit()) {
                    putString("email", "")
                    putString("password", "")
                    apply()
                }
            }
            mainViewModel.drawerAuthState.value = false
            mainViewModel.isModer.value = false
            mainViewModel.isAdmin.value = false
            mainViewModel.currentUser.value = User()
            navController.navigate("HomePage")
                         },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = colorResource(
                id = R.color.main_orange
            )),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.main_orange)),
            shape = CircleShape,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text("Выйти",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

    }


}
@Composable
private fun prepareAccountList(): List<NavigationDrawerItem>{
    val itemsList = arrayListOf<NavigationDrawerItem>()
    itemsList.add(
        NavigationDrawerItem(
            icon = ImageVector.vectorResource(id = R.drawable.profile),
            label = "Аккаунт"
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            icon = ImageVector.vectorResource(id = R.drawable.password),
            label = "Безопасность"
        )
    )
    return itemsList
}