package com.darrenfinch.mymealplanner.screens.mealplanform.controller

import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.validation.BaseFormValidator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.mealplans.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.screens.mealplanform.MealPlanFormData
import com.darrenfinch.mymealplanner.screens.mealplanform.MealPlanFormValidator
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class MealPlanFormController(
    private var screenData: MealPlanFormData,
    private val insertMealPlanUseCase: InsertMealPlanUseCase,
    private val screensNavigator: ScreensNavigator,
    private val mealPlanFormValidator: MealPlanFormValidator,
    private val toastsHelper: ToastsHelper,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext
) : StatefulController, MealPlanFormViewMvc.Listener, BackPressListener, BaseFormValidator.Listener {

    data class SavedState(val screenData: MealPlanFormData) :
        ControllerSavedState

    private lateinit var viewMvc: MealPlanFormViewMvc

    fun bindView(viewMvc: MealPlanFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
        mealPlanFormValidator.registerListener(this)

        viewMvc.hideProgressIndication()
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
        mealPlanFormValidator.unregisterListener(this)
    }

    fun bindMealDetailsToView() {
        viewMvc.bindMealPlanDetails(screenData.getMealPlanDetails())
    }

    override fun onDoneButtonClicked() {
        mealPlanFormValidator.testIsValidAndNotify(screenData)
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onTitleChange(newTitle: String) {
        screenData.setTitle(newTitle)
    }

    override fun onRequiredCaloriesChange(newRequiredCalories: Int) {
        screenData.setRequiredCalories(newRequiredCalories)
    }

    override fun onRequiredCarbohydratesChange(newRequiredCarbohydrates: Int) {
        screenData.setRequiredCarbohydrates(newRequiredCarbohydrates)
    }

    override fun onRequiredFatsChange(newRequiredFats: Int) {
        screenData.setRequiredFats(newRequiredFats)
    }

    override fun onRequiredProteinsChange(newRequiredProteins: Int) {
        screenData.setRequiredProteins(newRequiredProteins)
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            screenData = it.screenData
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(screenData)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.navigateUp()
        return true
    }

    override fun onValidateForm(validationResult: ValidationResult) {
        if (validationResult is ValidationResult.Success) {
            runBlocking(backgroundContext) {
                insertMealPlanUseCase.insertMealPlan(screenData.getMealPlanDetails())
            }
            screensNavigator.navigateUp()
        } else if (validationResult is ValidationResult.Failure) {
            toastsHelper.showShortMsg(validationResult.errorMsg)
        }
    }
}