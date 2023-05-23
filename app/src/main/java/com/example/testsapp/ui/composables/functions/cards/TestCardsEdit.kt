package com.example.testsapp.ui.composables.functions.cards

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.Test
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.RatingBar

@Composable
fun TestCardsEdit(navController: NavHostController, item: Test) {
    val openDialog = remember{
        mutableStateOf(false)
    }
    val test_name = remember {
        mutableStateOf(item.name)
    }
    val test_desc = remember {
        mutableStateOf(item.description)
    }
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable { navController.navigate("TestInfoPage/" + item.id) },
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
                style = MaterialTheme.typography.h1)
            Text(item.description,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2)
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

                FloatingActionButton(onClick = { openDialog.value = true },
                    modifier = Modifier
                        .size(42.dp),
                    shape = CircleShape,
                    backgroundColor = colorResource(id = R.color.main_orange),
                    elevation = FloatingActionButtonDefaults.elevation(5.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.edit_filled),
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
            }
        }

    }


    if (openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    Text(
                        "Изменить тест",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            },
            text = {
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = test_name.value,
                        onValueChange = { test_name.value = it },
                        label = { Text("Название") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colorResource(id = R.color.main_orange),
                            cursorColor = colorResource(id = R.color.main_orange),
                            trailingIconColor = colorResource(id = R.color.main_orange),
                            focusedBorderColor = colorResource(id = R.color.main_orange),
                            focusedLabelColor = colorResource(id = R.color.main_orange)
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = test_desc.value,
                        onValueChange = { test_desc.value = it },
                        label = { Text("Описание") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colorResource(id = R.color.main_orange),
                            cursorColor = colorResource(id = R.color.main_orange),
                            trailingIconColor = colorResource(id = R.color.main_orange),
                            focusedBorderColor = colorResource(id = R.color.main_orange),
                            focusedLabelColor = colorResource(id = R.color.main_orange)
                        )
                    )
                }
            },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.gray_background),
                            contentColor = Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            "Отмена",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1
                        )
                    }
                    Button(
                        onClick = {
                            changeTestInfo(item, test_name.value, test_desc.value)
                            Toast.makeText(context, "Изменения сохранены!", Toast.LENGTH_SHORT).show()
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.main_orange),
                            contentColor = Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            "Изменить",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1
                        )
                    }
                }
            }
        )
    }
}

fun changeTestInfo(item: Test, name: String, description: String) {
    item.name = name
    item.description = description
    SingletoneFirebase.instance.database.getReference("Tests").child(item.id).setValue(item)
}
