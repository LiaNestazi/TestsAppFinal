package com.example.testsapp.ui.composables.functions.cards

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testsapp.R
import com.example.testsapp.models.User
import com.example.testsapp.singletone.SingletoneFirebase

@Composable
fun UserCards(item: User) {
    val openDialog = remember{
        mutableStateOf(false)
    }
    val expanded = remember {
        mutableStateOf(false)
    }
    val user_role = remember {
        mutableStateOf(item.role)
    }
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp)
    ){
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                modifier = Modifier
                    .size(size = 100.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Profile Image"
            )
            Text(item.name,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2)
            Text(item.email,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(item.role,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.h1)

                FloatingActionButton(onClick = { openDialog.value = true },
                    modifier = Modifier
                        .size(32.dp),
                    shape = CircleShape,
                    backgroundColor = colorResource(id = R.color.main_orange),
                    elevation = FloatingActionButtonDefaults.elevation(5.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
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
                        "Изменить роль пользователя",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            },
            text = {
                Box(modifier = Modifier.fillMaxWidth()){
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Button(
                            onClick = {
                                expanded.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.main_orange),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text(
                                user_role.value,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.h1
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false })
                        {
                            DropdownMenuItem(
                                onClick = {
                                if (user_role.value == "Пользователь"){
                                    user_role.value = "Модератор"
                                } else{
                                    user_role.value = "Пользователь"
                                }
                                expanded.value = false
                            }) {
                                Text(text = if (user_role.value == "Пользователь") "Модератор" else "Пользователь",
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.body2)
                            }
                        }
                    }
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
                        shape = CircleShape
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
                            changeUserRole(item, user_role.value)
                            Toast.makeText(context, "Изменения сохранены!", Toast.LENGTH_SHORT).show()
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.main_orange),
                            contentColor = Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
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

fun changeUserRole(item: User, new_role: String) {
    item.role = new_role
    SingletoneFirebase.instance.database.getReference("Users").child(item.id).setValue(item)
}
