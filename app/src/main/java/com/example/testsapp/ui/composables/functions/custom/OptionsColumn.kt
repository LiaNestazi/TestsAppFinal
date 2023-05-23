package com.example.testsapp.ui.composables.functions.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.NavigationDrawerItem

@Composable
fun OptionsColumn(navController: NavHostController, list: List<NavigationDrawerItem>){
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 16.dp),

    ) {
        itemsIndexed(list)
        { index, item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                .clickable {
                when (item.label) {
                    "Аккаунт" -> {
                        navController.navigate("AccountPage", navOptions = null)
                    }
                    "Уведомления" -> {
                        navController.navigate("NotificationsPage", navOptions = null)
                    }
                    "Безопасность" -> {
                        navController.navigate("SecurityPage", navOptions = null)
                    }
                    "Сменить пароль" -> {
                        navController.navigate("ChangePasswordPage", navOptions = null)
                    }
                }
            }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.background(
                            colorResource(id = R.color.gray_background),
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "Icon",
                            modifier = Modifier.padding(9.dp)
                        )
                    }
                    Text(
                        item.label,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right),
                    contentDescription = "Arrow"
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}