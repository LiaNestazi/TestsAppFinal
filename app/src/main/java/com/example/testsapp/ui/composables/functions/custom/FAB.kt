package com.example.testsapp.ui.composables.functions.custom

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FAB(onClick: () -> Unit, modifier: Modifier = Modifier, backgroundColor: Color = Color.White, contentColor: Color = Color.Black, iconResourceId: Int){
    FloatingActionButton(onClick = { onClick() },
        modifier = modifier
            .size(45.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = FloatingActionButtonDefaults.elevation(5.dp)
    ){
        Icon(painter = painterResource(id = iconResourceId),"", modifier = Modifier.size(28.dp))
    }
}