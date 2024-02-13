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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Navigation.Screen
import com.example.myfirstcrudapp.Service.CandyService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUpdateCandyScreen(candyService: CandyService, navController: NavController?){
    val navBackStackEntry = navController?.currentBackStackEntry
    val candyNameArg = navBackStackEntry?.arguments?.getString("name")
    val candy = candyService.getCandyByName(candyNameArg)

    var newCandyPrice by remember { mutableStateOf(candy?.price.toString()) }
    var newCandyRecipe by remember { mutableStateOf(candy?.recipe.toString()) }
    var newCandyQuantity by remember { mutableStateOf(candy?.quantity.toString()) }
    var newCandyOfTheMonth by remember { mutableStateOf(candy?.candyOfTheMonth.toString()) }

    val adminUpdateButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFEC8F5E),
        contentColor = Color.Black
    )

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
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
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
        ,
        color = Color(0xFFF1EB90),
        contentColor = Color.Black
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)){
            Text(
                text = "${candy?.name}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = FontFamily.Cursive
            )
            Box (
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color.Black, CircleShape)){
                if (candy != null) {
                    Image(
                        painter = painterResource(id = candy.imageResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }

            TextField(
                value = newCandyPrice,
                onValueChange = { newCandyPrice = it },
                label = { Text("Candy Price") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color(0xFFF1EB90),
                    unfocusedIndicatorColor = Color(0xFFF1EB90)
                )
            )

            TextField(
                value = newCandyRecipe,
                onValueChange = { newCandyRecipe = it },
                label = { Text("Candy Recipe") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color(0xFFF1EB90),
                    unfocusedIndicatorColor = Color(0xFFF1EB90)
                )
            )

            TextField(
                value = newCandyQuantity,
                onValueChange = { newCandyQuantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color(0xFFF1EB90),
                    unfocusedIndicatorColor = Color(0xFFF1EB90)
                )
            )

            TextField(
                value = newCandyOfTheMonth,
                onValueChange = { newCandyOfTheMonth = it },
                label = { Text("Is candy of the month?(true/false)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color(0xFFF1EB90),
                    unfocusedIndicatorColor = Color(0xFFF1EB90)
                )
            )


            Button(onClick = {
                if (candy == null ||
                    newCandyPrice.trim().isEmpty() || newCandyRecipe.trim().isEmpty() ||
                    newCandyQuantity.trim().isEmpty() || newCandyOfTheMonth.trim().isEmpty() ||
                    newCandyPrice.trim().toFloatOrNull() == null || newCandyQuantity.trim().toIntOrNull() == null || !isBoolean(newCandyOfTheMonth.trim()) ||
                    newCandyPrice.trim().toFloat() < 0 || newCandyQuantity.trim().toInt() < 0) {
                    showErrorDialog = true
                }
                else{
                    candyService.updateCandy(
                        Candy(
                            candy.name.trim(),
                            newCandyPrice.trim().toFloat(),
                            newCandyQuantity.trim().toInt(),
                            newCandyRecipe.trim(),
                            newCandyOfTheMonth.trim().toBoolean(),
                            candy.imageResource)
                    )
                    showSuccessDialog = true
                }
             } ,
                colors = adminUpdateButtonColors,
                border = BorderStroke(1.dp, SolidColor(Color.Black)),
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Update")
            }
            Spacer(modifier = Modifier.height(8.dp))
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