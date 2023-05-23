package com.example.testsapp.ui.composables.pages.rolepages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.ui.composables.functions.cards.TestCardsEdit
import com.example.testsapp.viewmodels.MainViewModel

@Composable
fun TestsListPage(navController: NavHostController, mainViewModel: MainViewModel){
    Column(modifier = Modifier.fillMaxSize()) {
        Header(navController, title = "Список тестов")
        ShowLazyList(navController = navController, viewModel = mainViewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowLazyList(navController: NavHostController, viewModel: MainViewModel) {
    viewModel.getTests()

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(viewModel.tests.value){item ->
                TestCardsEdit(navController, item = item)
            }
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