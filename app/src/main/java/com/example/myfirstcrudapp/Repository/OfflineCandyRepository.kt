package com.example.myfirstcrudapp.Repository

import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Data.CandyDao
import kotlinx.coroutines.flow.Flow

class OfflineCandyRepository(private val candyDao: CandyDao) : CandyRepository{
    override fun getAllCandiesStream(): Flow<List<Candy>> = candyDao.getAllCandies()

    override fun getCandyStream(name: String): Flow<Candy?> = candyDao.getCandyByName(name)

    override suspend fun insertCandy(candy: Candy) = candyDao.insertCandy(candy)

    override suspend fun deleteCandy(candy: Candy) = candyDao.deleteCandy(candy)

    override suspend fun updateCandy(candy: Candy) = candyDao.updateCandy(candy)

}

