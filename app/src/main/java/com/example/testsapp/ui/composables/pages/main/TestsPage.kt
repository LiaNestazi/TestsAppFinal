package com.example.testsapp.ui.composables.pages.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.cards.TestCards
import com.example.testsapp.ui.composables.functions.custom.FAB
import com.example.testsapp.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun TestsPage(navController: NavHostController, viewModel: MainViewModel, scope: CoroutineScope, drawerState: DrawerState){

    Column(modifier = Modifier.fillMaxSize()) {
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
                        "Мои тесты",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(top = 8.dp, end = 6.dp)
                    )
                }
                Box(contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth(0.35f)
                ){
                    val action = {
                        navController.navigate("SearchPage/"+true)
                    }
                    FAB(action, iconResourceId = R.drawable.search)
                }
                Box(contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth(0.55f)
                ){
                    val expanded = remember {
                        mutableStateOf(false)
                    }
                    FAB({ expanded.value = !expanded.value}, iconResourceId = R.drawable.sort)
                    DropdownMenu(
                        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false })
                    {
                        DropdownMenuItem(onClick = {
                            expanded.value = false
                            sortListByNew(viewModel)
                        }) {
                            Text(text = "По новизне",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body2)
                        }
                        DropdownMenuItem(onClick = {
                            expanded.value = false
                            sortListByName(viewModel)
                        }) {
                            Text(text = "По названию",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body2)
                        }
                        DropdownMenuItem(onClick = {
                            expanded.value = false
                            sortListByRating(viewModel)
                        }) {
                            Text(text = "По рейтингу",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body2)
                        }
                    }
                }
            }
        }
        ShowLazyList(navController = navController, viewModel = viewModel)
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
                if (item.author_id == SingletoneFirebase.instance.auth.currentUser?.uid){
                    TestCards(navController, item = item)
                }
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

private fun sortListByNew(mainViewModel: MainViewModel){
    mainViewModel.sortTestsByNew()
}

private fun sortListByName(mainViewModel: MainViewModel){
    mainViewModel.sortTestsByName()
}

private fun sortListByRating(mainViewModel: MainViewModel){
    mainViewModel.sortTestsByRating()
}