package com.example.myfirstcrudapp.Screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Navigation.AdminModeViewModel
import com.example.myfirstcrudapp.R
import com.example.myfirstcrudapp.Repository.Repository
import com.example.myfirstcrudapp.Service.CandyService
import com.example.myfirstcrudapp.ui.theme.MyFirstCRUDAppTheme


fun populate(repository: Repository<Candy>) {
    val candies = listOf(
        Candy("Chocolate Bar", 2.5f, 100, "Milk chocolate with almonds", false, R.drawable.chocolate_bar),
        Candy("Gummy Bears", 1.0f, 200, "Assorted fruit flavors", true, R.drawable.gummy_bears),
        Candy("Licorice", 1.5f, 150, "Black licorice", false, R.drawable.licorice),
        Candy("Jelly Beans", 1.25f, 300, "Various fruit flavors", false, R.drawable.jelly_beans),
        Candy("Lollipop", 0.75f, 50, "Cherry flavor", false, R.drawable.lollipop),
        Candy("Toffee", 2.0f, 120, "Butter toffee", false, R.drawable.toffee),
        Candy("Hard Candy", 1.0f, 80, "Mixed fruit flavors", false, R.drawable.hard_candy),
        Candy("Caramel", 2.0f, 90, "Soft caramel", false, R.drawable.caramel),
        Candy("Jawbreaker", 0.5f, 400, "Multi-layered jawbreaker", false, R.drawable.jawbreaker),
        Candy("Peppermint", 1.0f, 60, "Peppermint twist", false, R.drawable.papermint)
    )

    candies.forEach { repository.addItem(it) }
}

@Composable
fun ShoppingCards(candyService: CandyService, navController: NavController?, adminModeViewModel: AdminModeViewModel) {
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
                candyService.getAllCandies()
            } else {
                // Return the existing list of candies
                candyService.getAllCandies()
            }
        }
    }
    val userButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Green,
        contentColor = Color.Black
    )

    val adminButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Red,
        contentColor = Color.Black
    )

    val adminMode = adminModeViewModel.adminMode.value
    Surface(modifier = Modifier.padding(bottom = 52.dp)) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row (modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically){
                Button(
                    onClick = {
                        adminModeViewModel.toggleAdminMode()
                    },
                    colors = if (!adminMode) adminButtonColors else userButtonColors,
                    border = BorderStroke(1.dp, SolidColor(Color.Black)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (adminMode) "User Mode" else "Admin Mode")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Candy Harmony",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            LazyColumn {
                items(candies) { candy ->
                    CandyCard(candy, navController, adminMode, candyService, onCandyRemoved)
                }
                item {
                    if (adminMode) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            IconButton(
                                onClick = { navController?.navigate("add_custom_candy") },
                                modifier = Modifier
                                    .size(75.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    tint = Color(0xFFEC8F5E),
                                    modifier = Modifier.size(50.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}





@Composable
fun CandyCard(candy : Candy, navController: NavController?, adminMode: Boolean, candyService: CandyService,
onCandyRemoved: () -> Unit
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(targetValue = if (isExpanded) 10.dp else 0.dp, label = "")

    val buyButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFEC8F5E),
        contentColor = Color.Black
    )

    var showDialog by remember { mutableStateOf(false) }

    Surface(
        color = Color(0xFFF3B664),
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
    ) {
        Column {
            Surface(
                color = Color(0xFFF1EB90),
                contentColor = Color.Black,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Image
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
                        // Candy Details
                        Text(
                            text = candy.name,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Cursive
                        )
                        Text(
                            text = "Price: $${candy.price}",
                            fontSize = 16.sp
                        )

                        if (isExpanded) {
                            // Additional Details when expanded
                            Text(
                                text = "Recipe: ${candy.recipe}",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Candy of the Month: ${if (candy.candyOfTheMonth) "Yes" else "No"}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    // Expand/Collapse Button
                    IconButton(
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) {
                                stringResource(R.string.show_less)
                            } else {
                                stringResource(R.string.show_more)
                            },
                            tint = Color.Black
                        )
                    }
                }
            }
            if (!adminMode){
            Button(
                onClick = {
                    navController?.navigate(route = "add_screen/${candy.name}")
                },
                colors = buyButtonColors,
                border = BorderStroke(1.dp, SolidColor(Color.Black)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Add to Basket",
                    fontSize = 16.sp
                )
            }}
            else {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center){
                    Button(onClick = { navController?.navigate("admin_update_screen/" + candy.name) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            containerColor = Color.Yellow
                        ),
                        border = BorderStroke(1.dp, SolidColor(Color.Black))
                    ) {
                        Text(text = "Update")
                    }
                    Spacer(modifier = Modifier.width(32.dp))
                    Button(onClick = {
                        showDialog = true
                    },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            containerColor = Color.Red
                        ),
                        border = BorderStroke(1.dp, SolidColor(Color.Black))) {
                        Text(text = "Delete")
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
                                    candyService.removeCandy(candy.name)
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingCardsPreview() {
    val candyRepository = Repository<Candy>()
    populate(candyRepository)
    val candyService = CandyService(candyRepository)
    MyFirstCRUDAppTheme {
        ShoppingCards(candyService, navController = null, adminModeViewModel = AdminModeViewModel())
    }
}