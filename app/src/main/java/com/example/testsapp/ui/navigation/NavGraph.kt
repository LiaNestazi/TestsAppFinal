package com.example.testsapp.ui.navigation

import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testsapp.ui.composables.pages.addtest.NameTestPage
import com.example.testsapp.ui.composables.pages.addtest.*
import com.example.testsapp.ui.composables.pages.main.*
import com.example.testsapp.ui.composables.pages.playtest.PlayTestPage
import com.example.testsapp.ui.composables.pages.playtest.ResultsPage
import com.example.testsapp.ui.composables.pages.rolepages.TestsListPage
import com.example.testsapp.ui.composables.pages.rolepages.UsersListPage
import com.example.testsapp.ui.composables.pages.settings.AccountPage
import com.example.testsapp.ui.composables.pages.userauth.RegisterPage
import com.example.testsapp.ui.composables.pages.userauth.SignInPage
import com.example.testsapp.ui.composables.pages.settings.ChangePasswordPage
import com.example.testsapp.ui.composables.pages.settings.SecurityPage
import com.example.testsapp.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    mainViewModel: MainViewModel
){
    NavHost(navController = navController,
        startDestination = "HomePage"
    ){
        composable("HomePage"){
            HomePage(navController, scope, drawerState, mainViewModel)
        }
        composable(
            route = "SearchPage/{author}",
            arguments = listOf(
                navArgument("author"){
                    type = NavType.BoolType
                },
            )
        ){
            SearchPage(navController = navController, mainViewModel, withAuthor = it.arguments?.getBoolean("author"))
        }
        composable("TestsPage"){
            TestsPage(navController,mainViewModel,scope, drawerState)
        }
        composable(
            route = "TestInfoPage/{current}",
            arguments = listOf(
                navArgument("current"){
                    type = NavType.StringType
                },
            )
        ){
            TestInfoPage(navController = navController, mainViewModel, item_id = it.arguments?.getString("current"))
        }


        composable("SettingsPage"){
            SettingsPage(navController,scope, drawerState, mainViewModel)
        }
        composable("AccountPage"){
            AccountPage(navController = navController, mainViewModel)
        }
        composable("SecurityPage"){
            SecurityPage(navController)
        }
        composable("ChangePasswordPage"){
            ChangePasswordPage(navController)
        }


        composable("AddTestPage"){
            NameTestPage(navController)
        }
        composable(route = "DescriptionPage"){
            DescriptionPage(navController)
        }
        composable(route = "TypeTestPage",
        ){
            TypeTestPage(navController)
        }
        composable(route = "QuestionsCountPage"){
            QuestionsCountPage(navController)
        }
        composable(
            route = "NQuestionTypePage/{currentN}",
            arguments = listOf(
                navArgument("currentN"){
                    type = NavType.IntType
                },
            )
        ){
            NQuestionTypePage(
                it.arguments?.getInt("currentN").toString().toInt(),
                navController = navController
            )
        }
        composable(
            route = "NQuestionContentPage/{currentN}",
            arguments = listOf(
                navArgument("currentN"){
                    type = NavType.IntType
                },
            )
        ){
            NQuestionContentPage(
                it.arguments?.getInt("currentN").toString().toInt(),
                navController = navController
            )
        }
        composable(route = "DurationPage"){
            DurationPage(navController = navController)
        }
        composable(route = "SuccessPage"){
            SuccessPage(navController)
        }

        //Authentication
        composable(route = "SignInPage"){
            SignInPage(navController, scope, drawerState, mainViewModel)
        }
        composable(route = "RegisterPage"){
            RegisterPage(navController, scope, drawerState, mainViewModel)
        }

        
        //Roles
        composable(route = "TestsListPage"){
            TestsListPage(navController = navController, mainViewModel)
        }
        composable(route = "UsersListPage"){
            UsersListPage(navController = navController,mainViewModel)
        }


        //Play
        composable(
            route = "PlayTestPage/{current}",
            arguments = listOf(
                navArgument("current"){
                    type = NavType.StringType
                },
            )
        ){
            PlayTestPage(navController = navController, mainViewModel, item_id = it.arguments?.getString("current"))
        }
        composable(
            route = "ResultsPage/{current}",
            arguments = listOf(
                navArgument("current"){
                    type = NavType.StringType
                },
            )
        ){
            ResultsPage(navController = navController, mainViewModel = mainViewModel, item_id = it.arguments?.getString("current"))
        }
    }
}