package com.example.myfirstcrudapp.Navigation

sealed class Screen(val route: String){
    object ShoppingListScreen : Screen("main_screen")
    object AddScreen: Screen("add_screen/{name}")
    object MyShoppingCartScreen: Screen("cart_screen")
    object UpdateScreen: Screen("update_screen/{name}")
    object AddCustomCandyScreen:  Screen("add_custom_candy")
    object AdminUpdateScreen: Screen("admin_update_screen/{name}")
}
