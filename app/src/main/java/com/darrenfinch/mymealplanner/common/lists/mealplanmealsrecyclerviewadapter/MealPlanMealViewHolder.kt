package com.darrenfinch.mymealplanner.common.lists.mealplanmealsrecyclerviewadapter

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.common.lists.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.utils.AnimationUtils
import com.darrenfinch.mymealplanner.databinding.MealPlanMealItemBinding
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class MealPlanMealViewHolder(private val listener: Listener, itemView: View) : BaseViewHolder<MealPlanMeal>(itemView) {
    interface Listener {
        fun onDelete(mealPlanMeal: MealPlanMeal)
    }

    private var binding = MealPlanMealItemBinding.bind(itemView)
    var expanded = ObservableBoolean(false)
    override fun bind(item: MealPlanMeal) {
        binding.mealPlanMeal = item
        binding.viewHolder = this
        binding.viewMoreButton.setOnClickListener {
            displayContextMenu()
        }
        initAdapter(item)
    }

    private fun displayContextMenu() {
        PopupMenu(itemView.context, binding.dropdownImageButton, Gravity.BOTTOM).apply {
            inflate(R.menu.context_menu)
            setOnMenuItemClickListener { menuItem ->
                handleMenuItemClicked(menuItem)
            }
            menu.removeItem(R.id.edit)
            show()
        }
    }

    private fun handleMenuItemClicked(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.delete -> listener.onDelete(binding.mealPlanMeal!!)
        }
        return true
    }

    fun inverseExpanded() {
        if (expanded.get()) AnimationUtils.collapse(binding.cardBottom) else AnimationUtils.expand(
            binding.cardBottom
        )
        expanded.set(!expanded.get())
    }

    private fun initAdapter(meal: MealPlanMeal) {
        val adapter = MealFoodsRecyclerViewAdapter()
        binding.mealFoodsRecyclerView.adapter = adapter
        binding.mealFoodsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        adapter.updateItems(meal.foods.toMutableList())
    }
}