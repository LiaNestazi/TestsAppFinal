package com.example.testsapp.ui.composables.functions.radiogroups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testsapp.R
import com.example.testsapp.models.Question
import com.example.testsapp.viewmodels.MainViewModel

@Composable
fun AnswersRadioGroup(startValue: String, mainViewModel: MainViewModel,questions: List<Question>) : Int{
    val mainOrange = colorResource(id = R.color.main_orange)
    val selectedOption = remember {
        mutableStateOf(startValue)
    }
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }
    when (questions.isEmpty()){
        true -> {
            CircularProgressIndicator(color = colorResource(id = R.color.main_orange))
            return -1
        }
        else -> {
            val question = questions[mainViewModel.selectedQuestion.value-1]
            val answers = question.answers
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                content = {
                    items(answers){ item ->
                        val isSelected = {
                            item == selectedOption.value
                        }
                        val textColor = {
                            if (isSelected()) Color.White else Color.Black
                        }
                        val buttonColor = {
                            if (isSelected()) mainOrange else Color.White
                        }
                        Card(
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(100.dp)
                                .clickable {
                                    onSelectionChange(item)
                                },
                            elevation = 5.dp,
                            backgroundColor = buttonColor()
                        ) {
                            Text(
                                item,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h1,
                                color = textColor()
                            )
                        }
                    }
                }
            )
            return answers.indexOf(selectedOption.value)
        }
    }
}