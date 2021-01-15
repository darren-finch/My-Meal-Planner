package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

interface SelectMealFoodQuantityViewMvc : ObservableViewMvc<SelectMealFoodQuantityViewMvc.Listener> {
    interface Listener {
        fun onFoodServingSizeChosen(foodBeforeUpdatingMacros: Food, selectedFoodQuantity: PhysicalQuantity)
    }

    fun bindFood(food: Food)
    fun makeDialog() : Dialog
}