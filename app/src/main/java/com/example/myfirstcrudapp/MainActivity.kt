package com.example.myfirstcrudapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Navigation.Navigation
import com.example.myfirstcrudapp.Repository.Repository
import com.example.myfirstcrudapp.Screen.populate
import com.example.myfirstcrudapp.Service.CandyService



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val candyRepository = Repository<Candy>()
        populate(candyRepository)
        val candyService = CandyService(candyRepository)

        val shoppingBasket = Repository<Candy>()
        val shoppingBasketService = CandyService(shoppingBasket)
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(candyService, shoppingBasketService)
        }
    }
}