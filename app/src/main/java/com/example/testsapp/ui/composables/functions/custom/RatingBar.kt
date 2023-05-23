package com.example.testsapp.ui.composables.functions.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testsapp.R

@Composable
fun RatingBar(
    rating: Int,
    changeable: Boolean = false
): Int{
    val ratingState = remember {
        mutableStateOf(rating)
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        for (i in 1..5){
            Icon(
                painter = painterResource(id = R.drawable.star_icon),
                contentDescription = "Star",
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
                    .clickable {
                        if (changeable) ratingState.value = i
                    },
                tint =
                if (i<=ratingState.value) colorResource(id = R.color.yellow)
                else colorResource(id = R.color.light_gray)
            )
        }
    }
    return ratingState.value
}