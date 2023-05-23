package com.example.testsapp.ui.composables.pages.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.cards.TestCards
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel

@Composable
fun SearchPage(navController: NavHostController, viewModel: MainViewModel, withAuthor: Boolean?){
    val searchRequest = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Header(navController, title = "Поиск")
        Column(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = searchRequest.value,
                onValueChange = {
                    searchRequest.value = it
                },
                placeholder = {
                    Row {
                        Icon(painter = painterResource(id = R.drawable.search),
                            contentDescription = "",
                            tint = colorResource(id = R.color.light_gray))
                        Text("Введите запрос или id",
                            style = MaterialTheme.typography.body1,
                            color = colorResource(id = R.color.light_gray),
                            modifier = Modifier.padding(start = 4.dp)
                        )}
                },
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.main_orange),
                    cursorColor = colorResource(id = R.color.main_orange),
                    trailingIconColor = colorResource(id = R.color.main_orange),
                    focusedBorderColor = colorResource(id = R.color.main_orange),
                    unfocusedBorderColor = colorResource(id = R.color.light_gray)
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Результат поиска",
                fontSize = 20.sp,
                style = MaterialTheme.typography.h1)
            ShowLazyList(navController = navController, viewModel = viewModel, searchRequest.value, withAuthor)
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowLazyList(navController: NavHostController, viewModel: MainViewModel, searchRequest: String, withAuthor: Boolean?) {
    viewModel.getTests()

    val nothingFound = remember {
        mutableStateOf(true)
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(viewModel.tests.value){item ->
                if (withAuthor == true){
                    if (item.author_id == SingletoneFirebase.instance.auth.currentUser?.uid){
                        if (searchRequest != ""){
                            if (item.name.contains(searchRequest)||
                                item.description.contains(searchRequest) ||
                                item.id.contains(searchRequest) ||
                                item.author_login.contains(searchRequest)
                            ){
                                TestCards(navController, item = item)
                                nothingFound.value = false
                            }
                        } else{
                            nothingFound.value = true
                        }
                    }
                } else{
                    if (searchRequest != ""){
                        if (item.name.contains(searchRequest)||
                            item.description.contains(searchRequest) ||
                            item.id.contains(searchRequest) ||
                            item.author_login.contains(searchRequest)
                        ){
                            TestCards(navController, item = item)
                            nothingFound.value = false
                        }
                    } else{
                        nothingFound.value = true
                    }
                }
            }
        }
        if (nothingFound.value){
            Text(text = "Ничего не найдено",
                fontSize = 16.sp,
                style = MaterialTheme.typography.body2,
                color = colorResource(id = R.color.light_gray)
            )
        }
        if (viewModel.tests.value.isEmpty()){
            Text(text = "Здесь пока ничего нет",
                fontSize = 16.sp,
                style = MaterialTheme.typography.body2,
                color = colorResource(id = R.color.light_gray)
            )
        }
    }
}