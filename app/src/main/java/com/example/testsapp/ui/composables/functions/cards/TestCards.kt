package com.example.testsapp.ui.composables.functions.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.models.Test
import com.example.testsapp.R
import com.example.testsapp.ui.composables.functions.custom.RatingBar

@Composable
fun TestCards(navController: NavHostController, item: Test) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable { navController.navigate("TestInfoPage/"+item.id) },
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp)
    ){
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(item.name,
                fontSize = 28.sp,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center)
            Text(item.description,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text("Рейтинг",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body2)
                RatingBar(rating = item.rating)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(item.author_login,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body2)

                FloatingActionButton(onClick = {
                    navController.navigate("PlayTestPage/"+item.id)
                },
                    modifier = Modifier
                        .size(42.dp),
                    shape = CircleShape,
                    backgroundColor = colorResource(id = R.color.main_orange),
                    elevation = FloatingActionButtonDefaults.elevation(5.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Start",
                        tint = Color.White
                    )
                }
            }
        }

    }
}