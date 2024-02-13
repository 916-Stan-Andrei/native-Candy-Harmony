package com.example.myfirstcrudapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Candy(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val price: Float,
    val quantity: Int,
    val recipe: String,
    val candyOfTheMonth: Boolean,
    val imageResource: Int
)

