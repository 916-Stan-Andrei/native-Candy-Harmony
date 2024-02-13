package com.example.myfirstcrudapp.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Navigation.Screen
import com.example.myfirstcrudapp.R
import com.example.myfirstcrudapp.Service.CandyService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomCandyScreen(candyService: CandyService, navController: NavController?){
    var candyName by remember { mutableStateOf("") }
    var candyPrice by remember { mutableStateOf("") }
    var candyRecipe by remember { mutableStateOf("") }
    var candyQuantity by remember { mutableStateOf("") }
    var candyOfTheMonth by remember { mutableStateOf("") }

    val addCustomCandyButtonColors = ButtonDefaults.buttonColors(
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
            .padding(top = 100.dp)
            .padding(bottom = 150.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        color = Color(0xFFF1EB90),
        contentColor = Color.Black
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)) {
            Text(
                text = "New Candy Details",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = FontFamily.Cursive
            )

            TextField(
                value = candyName,
                onValueChange = { candyName = it },
                label = { Text("Candy Name") },
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
                value = candyPrice,
                onValueChange = { candyPrice = it },
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
                value = candyRecipe,
                onValueChange = { candyRecipe = it },
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
                value = candyQuantity,
                onValueChange = { candyQuantity = it },
                label = { Text("Candy Quantity") },
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
                value = candyOfTheMonth,
                onValueChange = { candyOfTheMonth = it },
                label = { Text("Is candy of the month?(true/false)") },
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
            Button(onClick = {
                if (candyService.getCandyByName(candyName.trim()) != null ||
                    candyName.trim().isEmpty() || candyPrice.trim().isEmpty() || candyRecipe.trim().isEmpty() ||
                    candyQuantity.trim().isEmpty() || candyOfTheMonth.trim().isEmpty() ||
                    candyPrice.trim().toFloatOrNull() == null || candyQuantity.trim().toIntOrNull() == null || !isBoolean(candyOfTheMonth.trim()) ||
                    candyPrice.trim().toFloat() < 0 || candyQuantity.trim().toInt() < 0
                    ) {
                    showErrorDialog = true
                }else{
                    candyService.addCandy(
                        Candy(
                            candyName.trim(),
                            candyPrice.trim().toFloat(),
                            candyQuantity.trim().toInt(),
                            candyRecipe.trim(),
                            candyOfTheMonth.trim().toBoolean(),
                            R.drawable.custom_candy
                        )
                    )
                    showSuccessDialog = true
                }
            } ,
                colors = addCustomCandyButtonColors,
                border = BorderStroke(1.dp, SolidColor(Color.Black)),
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = "Create")
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

fun isBoolean(input: String): Boolean {
    return input.equals("true", ignoreCase = true) || input.equals("false", ignoreCase = true)
}
