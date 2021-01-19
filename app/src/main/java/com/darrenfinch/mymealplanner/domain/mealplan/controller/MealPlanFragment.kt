package com.darrenfinch.mymealplanner.domain.mealplan.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog
import com.darrenfinch.mymealplanner.domain.mealplan.view.MealPlanViewMvc

class MealPlanFragment : BaseFragment() {

    companion object {
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        fun newInstance() = MealPlanFragment()
    }

    private lateinit var viewMvc: MealPlanViewMvc
    private lateinit var controller: MealPlanController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getMealPlanController()
        listenForSelectMealPlanMealDialogResults()
    }

    private fun listenForSelectMealPlanMealDialogResults() {
        childFragmentManager.setFragmentResultListener(SelectMealPlanMealDialog.TAG, this, FragmentResultListener { requestKey, result ->
            controller.setDialogResults(requestKey, result)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getMealPlanViewMvc(null)

        restoreControllerState(savedInstanceState)
        controller.bindView(viewMvc)
        controller.fetchAllMealPlans(viewLifecycleOwner)

        return viewMvc.getRootView()
    }

    fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as MealPlanController.SavedState)
        }
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(bundleOf(CONTROLLER_SAVED_STATE to controller.getState()))
    }
}
