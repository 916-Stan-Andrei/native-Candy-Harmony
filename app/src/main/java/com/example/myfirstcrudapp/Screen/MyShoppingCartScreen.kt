package com.example.myfirstcrudapp.Screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.R
import com.example.myfirstcrudapp.Repository.Repository
import com.example.myfirstcrudapp.Service.CandyService
import com.example.myfirstcrudapp.ui.theme.MyFirstCRUDAppTheme


@Composable
fun ShoppingCartCards(shoppingBasketService: CandyService, navController: NavController?){
    var refreshPage by remember { mutableStateOf(false) }

    val onCandyRemoved: () -> Unit = {
        refreshPage = true
    }
    // If the page needs to be refreshed, update the candies list
    val candies by remember(refreshPage) {
        derivedStateOf {
            if (refreshPage) {
                // Reset the refreshPage state
                refreshPage = false
                // Return the updated list of candies
                shoppingBasketService.getAllCandies()
            } else {
                // Return the existing list of candies
                shoppingBasketService.getAllCandies()
            }
        }
    }
    Surface (modifier = Modifier.padding(bottom = 72.dp)){
        Column (modifier = Modifier.padding(4.dp)) {
            if (candies.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.RemoveShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Your basket is empty!",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
            else{
            LazyColumn{
                items(candies){ candy ->
                    ShoppingCartCard(candy, shoppingBasketService, navController, onCandyRemoved)
                }
            }
            }
        }
    }
}

@Composable
fun ShoppingCartCard(candy: Candy, shoppingBasketService: CandyService ,navController: NavController?,
                     onCandyRemoved: () -> Unit) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(targetValue = if (isExpanded) 10.dp else 0.dp, label = "")

    var showDialog by remember { mutableStateOf(false) }

    Surface(
        color = Color(0xFF60FAF8), contentColor = Color.Black,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp))

    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box (
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color.Black, CircleShape)){
                Image(
                    painter = painterResource(id = candy.imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(
                    text = candy.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive
                )
                Text(
                    text = "Price: $${candy.price}"
                )
                Text(
                    text = "Quantity: ${candy.quantity}"
                )
                if (isExpanded) {
                    Text(
                        text = "Recipe: ${candy.recipe}"
                    )
                    Text(
                        text = "Candy of the Month: ${if (candy.candyOfTheMonth) "Yes" else "No"}",
                    )
                }
            }
            Row {
                IconButton(
                    onClick = {
                        navController?.navigate("update_screen/" + candy.name)
                    }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Confirmation")
            },
            text = {
                Text(text = "Are you sure you want to remove this candy from your cart?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        shoppingBasketService.removeCandy(candy.name)
                        onCandyRemoved()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingCartCardsPreview() {
    val shoppingBasketRepository = Repository<Candy>();
    populate(shoppingBasketRepository);
    val shoppingBasketService = CandyService(shoppingBasketRepository);
    MyFirstCRUDAppTheme {
        ShoppingCartCards(shoppingBasketService, navController = null)
    }
}