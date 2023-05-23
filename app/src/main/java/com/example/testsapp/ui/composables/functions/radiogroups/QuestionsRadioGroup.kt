package com.example.testsapp.ui.composables.functions.radiogroups

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testsapp.R
import com.example.testsapp.viewmodels.MainViewModel

@Composable
fun QuestionsRadioGroup(mainViewModel: MainViewModel, questionCount: Int){
    val mainOrange = colorResource(id = R.color.main_orange)
    val list = mutableListOf<String>()
    for (question_number in 1..questionCount){
        list.add(question_number.toString())
    }
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .verticalScroll(rememberScrollState()),
    ) {
        items(list){ question_number ->
            val isSelected = {
                question_number == mainViewModel.selectedQuestion.value.toString()
            }
            val textColor = {
                if (isSelected()) Color.White else Color.Black
            }
            val buttonColor = {
                if (isSelected()) mainOrange else Color.White
            }
            FloatingActionButton(onClick = {},
                modifier = Modifier
                    .size(45.dp),
                shape = RoundedCornerShape(15.dp),
                backgroundColor = buttonColor(),
                contentColor = textColor(),
                elevation = FloatingActionButtonDefaults.elevation(5.dp)
            ){
                Text(text = question_number,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h1
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

        }
    }
}