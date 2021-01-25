package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealUseCase(private val repository: MainRepository) {
    suspend fun deleteMeal(id: Int) {
        repository.deleteMeal(id)

        // TODO: REMOVE MEAL FOODS
    }
}