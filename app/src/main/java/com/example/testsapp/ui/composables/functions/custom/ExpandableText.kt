package com.example.testsapp.ui.composables.functions.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testsapp.R

@Composable
fun ExpandableText(isVisible: Boolean, content: String){
    if (isVisible){
        Text(content,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 16.dp),
            fontSize = 14.sp,
            style = MaterialTheme.typography.body2,
            color = colorResource(id = R.color.light_gray)
        )
    }
}