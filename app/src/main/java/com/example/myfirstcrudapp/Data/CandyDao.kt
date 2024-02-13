package com.example.myfirstcrudapp.Data;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfirstcrudapp.Model.Candy
import kotlinx.coroutines.flow.Flow

@Dao
interface CandyDao {
    @Insert
    suspend fun insertCandy(candy: Candy)

    @Update
    suspend fun updateCandy(candy: Candy)

    @Delete
    suspend fun deleteCandy(candy: Candy)

    @Query("SELECT * from candy WHERE name = :name")
    fun getCandyByName(name: String): Flow<Candy>

    @Query("SELECT * FROM candy")
    fun getAllCandies() : Flow<List<Candy>>





}
