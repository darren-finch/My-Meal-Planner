package com.darrenfinch.mymealplanner.domain.mealplan.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.mealplan.view.MealPlanViewMvc

class MealPlanFragment : BaseFragment() {

    companion object {
        fun newInstance() = MealPlanFragment()
    }

    private val viewModel: MealPlanViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private lateinit var viewMvc: MealPlanViewMvc
    private lateinit var controller: MealPlanController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getMealPlanController(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getMealPlanViewMvc(null)

        controller.setState(savedInstanceState ?: arguments)
        controller.bindView(viewMvc)

        return viewMvc.getRootView()
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(viewLifecycleOwner)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(controller.getState())
    }
}