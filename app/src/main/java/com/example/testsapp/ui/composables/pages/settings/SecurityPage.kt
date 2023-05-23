package com.example.testsapp.ui.composables.pages.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.NavigationDrawerItem
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.ui.composables.functions.custom.OptionsColumn

@Composable
fun SecurityPage(navController: NavHostController){
    Column {
        Header(navController = navController, title = "Безопасность")
        val securityList = prepareSecurityList()
        OptionsColumn(navController, list = securityList)
    }

}

@Composable
private fun prepareSecurityList(): List<NavigationDrawerItem>{
    val itemsList = arrayListOf<NavigationDrawerItem>()
    itemsList.add(
        NavigationDrawerItem(
            icon = ImageVector.vectorResource(id = R.drawable.password),
            label = "Сменить пароль"
        )
    )
    return itemsList
}