package com.example.myfirstcrudapp.Screen

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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Service.CandyService
import kotlinx.coroutines.delay

@Composable
fun UpdateCardScreen(shoppingBasketService: CandyService, navController: NavController?) {
    val navBackStackEntry = navController?.currentBackStackEntry
    val candyNameArg = navBackStackEntry?.arguments?.getString("name")
    val candy = shoppingBasketService.getCandyByName(candyNameArg)

    var quantity by remember { mutableStateOf(0) }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EditDialog(onDismiss = { showDialog = false })
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .padding(top = 48.dp)
        .padding(bottom = 85.dp)
        .clip(RoundedCornerShape(15.dp))
        .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        color = Color(0xFF60FAF8),
        contentColor = Color.Black){
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
                        shoppingBasketService.updateCandy(
                            Candy(
                                candy.name,
                                candy.price,
                                quantity,
                                candy.recipe,
                                candy.candyOfTheMonth,
                                candy.imageResource
                            )
                        )
                        showDialog = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = quantity > 0 && candy.quantity != quantity
                ) {
                    Text("Save edit", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { navController?.navigate(route = "cart_screen")}
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
fun EditDialog(onDismiss: () -> Unit) {
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
                        .background(Color(0xFF00FF00), shape = RoundedCornerShape(16.dp)) // Green background
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Quantity changed!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
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
