package com.example.myfirstcrudapp.Navigation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myfirstcrudapp.Screen.AddCustomCandyScreen
import com.example.myfirstcrudapp.Screen.AddToCartScreen
import com.example.myfirstcrudapp.Screen.AdminUpdateCandyScreen
import com.example.myfirstcrudapp.Service.CandyService
import com.example.myfirstcrudapp.Screen.ShoppingCards
import com.example.myfirstcrudapp.Screen.ShoppingCartCards
import com.example.myfirstcrudapp.Screen.UpdateCardScreen


@Composable
fun NavigationBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        BottomAppBar(
            containerColor = Color(0xFFEC8F5E),
            contentColor = Color.Black,
            modifier = Modifier.height(56.dp)
        ) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = {
                        navController.navigate("main_screen") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(Icons.Default.List, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(32.dp))
                IconButton(
                    onClick = {
                        navController.navigate("cart_screen") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(Icons.Default.ShoppingBasket, contentDescription = null)
                }
            }
        }
    }
}

class AdminModeViewModel : ViewModel() {
    var adminMode = mutableStateOf(false)
    fun toggleAdminMode() {
        adminMode.value = !adminMode.value
    }
}
@Composable
fun Navigation(candyService: CandyService, shoppingBasketService: CandyService){
    val navController = rememberNavController()
    val adminModeViewModel: AdminModeViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.ShoppingListScreen.route){
        composable(route = Screen.ShoppingListScreen.route){
            ShoppingCards(candyService, navController = navController, adminModeViewModel)
        }
        composable(
            route = Screen.AddScreen.route,
            arguments = listOf(navArgument("name"){
                type = NavType.StringType
            })
        ) {
            AddToCartScreen(candyService, shoppingBasketService, navController = navController)
        }
        composable(route = Screen.MyShoppingCartScreen.route){
            ShoppingCartCards(shoppingBasketService = shoppingBasketService, navController = navController)
        }
        composable(
            route = Screen.UpdateScreen.route,
            arguments = listOf(navArgument("name"){
                type = NavType.StringType
            })
        ) {
            UpdateCardScreen(shoppingBasketService, navController = navController)
        }
        composable(route = Screen.AddCustomCandyScreen.route){
            AddCustomCandyScreen(candyService = candyService, navController = navController)
        }
        composable(route = Screen.AdminUpdateScreen.route,
            arguments = listOf(navArgument("name"){
                type = NavType.StringType
            })
        ){
            AdminUpdateCandyScreen(candyService, navController)
        }
    }
    NavigationBar(navController = navController)
}
