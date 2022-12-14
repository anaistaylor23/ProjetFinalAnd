package com.example.mylinkedin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mylinkedin.ui.theme.*
import androidx.compose.foundation.layout.Column as Column1


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel: MainViewModel by viewModels()
        setContent {
            MyLinkedinTheme() {
                // A surface container using the 'background' color from the theme
                Column1() {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        var color = MaterialTheme.colors.background
                        NavControl(viewModel = viewmodel)
                    }

                }
            }
        }
    }





@Composable
fun NavControl(viewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(

        topBar = { TopBar(navController) },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Movie, contentDescription = "Films") },
                    label = { Text("Film") },
                    selected = currentDestination?.hierarchy?.any { it.route == "Film" } == true,
                    onClick = {
                        navController.navigate("Film") }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.PlayArrow, contentDescription = null) },
                    label = { Text("Series") },
                    selected = currentDestination?.hierarchy?.any { it.route == "Series" } == true,
                    onClick = {
                        navController.navigate("Series") }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text("Personnes") },
                    selected = currentDestination?.hierarchy?.any { it.route == "Personnes" } == true,
                    onClick = {
                        navController.navigate("Personnes")
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, modifier = Modifier, startDestination = "Profil") {
            composable("Profil") {
                Profile(navController = navController)
            }

            composable("Film") {
                Film(navController = navController, viewModel = viewModel)
            }
            composable("Series") {
                Serie(navController = navController, viewModel = viewModel)
            }

            composable("filmd" + "/{id}") { NavBackStack ->
                val id = NavBackStack.arguments?.getString("id")
                if (id != null) {
                    DetailFilm(navController, id, viewModel)
                }
            }
            composable("seried" + "/{id}") { NavBackStack ->
                val id = NavBackStack.arguments?.getString("id")
                if (id != null) {
                    DetailSerie(navController, id, viewModel)
                }
            }
            composable("personnes"){
                Personnes(navController,viewModel)
            }
            composable("personned" + "/{id}") { NavBackStack ->
                val id = NavBackStack.arguments?.getString("id")
                if (id != null) {
                    DetailPersonne(navController, id, viewModel)
                }
            }

        }}
}}


















