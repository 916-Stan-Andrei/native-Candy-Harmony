package com.example.myfirstcrudapp.Service

import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Repository.Repository

class   CandyService(private val candyRepository: Repository<Candy>) {

    fun addCandy(candy: Candy) {
        candyRepository.addItem(candy)
    }

    fun getAllCandies(): List<Candy> {
        return candyRepository.getAllItems()
    }

    fun getCandyByName(name: String?): Candy? {
        return candyRepository.getItemBy { it.name == name }
    }

    fun updateCandy(candy: Candy) {
        candyRepository.updateItem(candy) { it.name == candy.name }
    }

    fun removeCandy(name: String) {
        candyRepository.removeItem { it.name == name }
    }
}
