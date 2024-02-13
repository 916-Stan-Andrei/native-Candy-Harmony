package com.example.myfirstcrudapp.Repository

import com.example.myfirstcrudapp.Model.Candy
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of candy from a given data source.
 */
interface CandyRepository {
    /**
     * Retrieve all the items from the given data source.
     */
    fun getAllCandiesStream(): Flow<List<Candy>>

    /**
     * Retrieve an item from the given data source that matches with the name.
     */
    fun getCandyStream(name: String): Flow<Candy?>

    /**
     * Insert item in the data source
     */
    suspend fun insertCandy(candy: Candy)

    /**
     * Delete item from the data source
     */
    suspend fun deleteCandy(candy: Candy)

    /**
     * Update item in the data source
     */
    suspend fun updateCandy(candy: Candy)
}
