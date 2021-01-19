package com.darrenfinch.mymealplanner.domain.mealplan.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.*
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

// TODO: Reimplement this controller
class MealPlanController(
    private val getAllMealPlansUseCase: GetAllMealPlansUseCase,
    private val getMealsForMealPlanUseCase: GetMealsForMealPlanUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val deleteMealPlanMealUseCase: DeleteMealPlanMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager
) : BaseController, MealPlanViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

    private lateinit var viewMvc: MealPlanViewMvc

    fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchAllMealPlans(viewLifecycleOwner: LifecycleOwner) {
        // TODO: Fetch all meal plans, and when notified, update spinner and update list of meal plan meals
    }

    override fun onMealPlanSelected(index: Int) {
        // TODO: Get meals for meal plan with index
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.navigateToMealPlanFormScreen()
    }
    override fun onDeleteMealPlanClicked() {
        // TODO: Update meal plan selection spinner and list of meal plan meals
    }

    override fun onAddNewMealPlanMealClicked() {
        dialogsManager.showSelectMealPlanMealDialog()
    }
    override fun onDeleteMealPlanMealClicked(mealPlanMeal: MealPlanMeal) {
        // TODO: Delete meal and update list of meals
    }

    override fun restoreState(state: BaseController.BaseSavedState) {}
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }

    fun setDialogResults(requestKey: String, result: Bundle) {
        // TODO: Insert new meal plan meal if it came from SelectMealPlanMealDialog
    }
}