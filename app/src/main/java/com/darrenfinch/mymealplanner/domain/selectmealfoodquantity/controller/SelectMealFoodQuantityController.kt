package com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class SelectMealFoodQuantityController(
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val foodId: Int,
    private val currentMeal: Meal
) : SelectMealFoodQuantityViewMvc.Listener {

    private lateinit var viewMvc: SelectMealFoodQuantityViewMvc

    fun bindView(viewMvc: SelectMealFoodQuantityViewMvc) {
        this.viewMvc = viewMvc
    }

    fun fetchFood(viewLifecycleOwner: LifecycleOwner) {
        getFoodUseCase.fetchFood(foodId).observe(viewLifecycleOwner, Observer {
            viewMvc.bindFood(it)
        })
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onMealFoodQuantityChosen(updatedMealFood: MealFood) {
        screensNavigator.navigateFromSelectMealFoodQuantityScreenToAddEditMealScreen(
            updatedMealFood,
            currentMeal
        )
    }
}
