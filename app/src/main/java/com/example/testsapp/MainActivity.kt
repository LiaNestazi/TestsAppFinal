package com.example.testsapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.testsapp.ui.composables.pages.main.MainPage
import com.example.testsapp.ui.theme.TestsAppTheme
import com.example.testsapp.viewmodels.MainViewModel


class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels()
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        setContent {
            TestsAppTheme {
                when (isOnline(context)){
                    true -> {
                        MainPage(mainViewModel)
                    }
                    else -> {
                        AlertDialog(
                            onDismissRequest = {

                            },
                            title = {
                                Text(
                                    "Вы не подключены к интернету!",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h1,
                                    modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth()
                                )
                            },
                            text = {
                                Text(
                                    "Проверьте подключение и попробуйте снова",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth()
                                )
                            },
                            buttons = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = {
                                            finish()
                                            startActivity(intent)
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
                                            "Перезагрузить",
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
            }
        }
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
    }
    return false
}












