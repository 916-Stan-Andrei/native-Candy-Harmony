package com.example.myfirstcrudapp.ViewModel;


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myfirstcrudapp.Model.Candy
import com.example.myfirstcrudapp.Repository.CandyRepository

class CandyEntryViewModel(
    private val candyRepository: CandyRepository
) : ViewModel() {

    var candyUiState by mutableStateOf(CandyUiState())
        private set

    fun updateUiState(candyDetails: CandyDetails){
        candyUiState =  CandyUiState(candyDetails = candyDetails, isEntryValid = validateInput(candyDetails))
    }

    suspend fun saveCandy(){
        if(validateInput()){
            candyRepository.insertCandy(candyUiState.candyDetails.toCandy())
        }
    }
    private fun validateInput(uiState: CandyDetails = candyUiState.candyDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank() &&
                    recipe.isNotBlank() && candyOfTheMonth.isNotBlank() && imageResource.isNotBlank()
        }
    }
}

data class CandyUiState(
    val candyDetails: CandyDetails = CandyDetails(),
    val isEntryValid: Boolean = false
)

data class CandyDetails(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val recipe: String = "",
    val candyOfTheMonth: String = "",
    val imageResource: String = ""
)

fun CandyDetails.toCandy() : Candy = Candy(
    name = name,
    price = price.toFloatOrNull() ?: 0F,
    quantity = quantity.toIntOrNull() ?: 0,
    recipe = recipe,
    candyOfTheMonth = candyOfTheMonth.toBooleanStrictOrNull() ?:false,
    imageResource = imageResource.toIntOrNull() ?: -1
)

fun Candy.toCandyUiState(isEntryValid: Boolean = false): CandyUiState = CandyUiState(
    candyDetails = this.toCandyDetails(),
    isEntryValid = isEntryValid
)

fun Candy.toCandyDetails(): CandyDetails = CandyDetails(
    name = name,
    price = price.toString(),
    quantity = quantity.toString(),
    recipe = recipe,
    candyOfTheMonth = candyOfTheMonth.toString(),
    imageResource = imageResource.toString()
)


