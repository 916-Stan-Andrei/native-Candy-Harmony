package com.example.myfirstcrudapp.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Navigation.Screen
import com.example.myfirstcrudapp.Service.CandyService
import kotlinx.coroutines.delay

@Composable
fun AddToCartScreen(candyService: CandyService, shoppingBasketService: CandyService, navController: NavController?) {
    val navBackStackEntry = navController?.currentBackStackEntry
    val candyNameArg = navBackStackEntry?.arguments?.getString("name")
    val candy = candyService.getCandyByName(candyNameArg)

    var quantity by remember { mutableStateOf(0) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val buyButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFEC8F5E),
        contentColor = Color.Black
    )

    if (showSuccessDialog) {
        SuccessDialog(onDismiss = { showSuccessDialog = false })
    }
    if (showErrorDialog) {
        ErrorDialog(onDismiss = { showErrorDialog = false })
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 48.dp)
            .padding(bottom = 85.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        color = Color(0xFFF1EB90),
        contentColor = Color.Black
    ) {
        if (candy != null) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box (
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(2.dp, Color.Black, CircleShape)){
                    Image(
                        painter = painterResource(id = candy.imageResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Text(
                    text = candy.name,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive
                )
                Text(
                    text = "Recipe: ${candy.recipe}",
                    fontSize = 16.sp
                )
                Text(
                    text = "Price/piece: $${candy.price}",
                    fontSize = 18.sp
                )
                Text(
                    text = "Candy of the Month: ${if (candy.candyOfTheMonth) "Yes" else "No"}",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Quantity: $quantity",
                        fontSize = 18.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity > 0) {
                                    quantity--
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease Quantity"
                            )
                        }

                        IconButton(
                            onClick = {
                                quantity++
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase Quantity"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (shoppingBasketService.getCandyByName(candy.name) == null){
                            shoppingBasketService.addCandy(
                                Candy(
                                    candy.name,
                                    candy.price,
                                    quantity,
                                    candy.recipe,
                                    candy.candyOfTheMonth,
                                    candy.imageResource
                                )
                            )
                            showSuccessDialog = true
                        }
                        else{
                            showErrorDialog = true
                        }

                    },
                    colors = buyButtonColors,
                    border = BorderStroke(1.dp, SolidColor(Color.Black)),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = quantity > 0
                ) {
                    Text("Add to Cart", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { navController?.navigate(route = "main_screen",
                            builder = {
                                popUpTo(Screen.ShoppingListScreen.route) {
                                    inclusive = false // Set to true if you also want to remove the destination_screen from the back stack
                                }
                                launchSingleTop = true // Optional, this will ensure that if the destination is already on top of the stack, it won't be recreated
                            })}
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    var dialogVisible by remember { mutableStateOf(true) }

    if (dialogVisible) {
        Dialog(
            onDismissRequest = {
                onDismiss()
                dialogVisible = false
            },
            content = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ErrorOutline,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Error adding to cart!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        )

        LaunchedEffect(dialogVisible) {
            delay(1000)
            dialogVisible = false
            onDismiss()
        }
    }
}

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    var dialogVisible by remember { mutableStateOf(true) }

    if (dialogVisible) {
        Dialog(
            onDismissRequest = {
                onDismiss()
                dialogVisible = false
            },
            content = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF00FF00), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Added to Cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        )

        LaunchedEffect(dialogVisible) {
            delay(1000) //
            dialogVisible = false
            onDismiss()
        }
    }
}

