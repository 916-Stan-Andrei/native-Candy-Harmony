package com.example.myfirstcrudapp.AppContainer

import android.content.Context
import com.example.myfirstcrudapp.Data.CandyDatabase
import com.example.myfirstcrudapp.Repository.CandyRepository
import com.example.myfirstcrudapp.Repository.OfflineCandyRepository



interface AppContainer {
    val candyRepository: CandyRepository
}
class AppDataContainer (private val context: Context): AppContainer{
    override val candyRepository: CandyRepository by lazy {
        OfflineCandyRepository(CandyDatabase.getDatabase(context).candyDao())
    }
}