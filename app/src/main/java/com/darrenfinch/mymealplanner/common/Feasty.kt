package com.darrenfinch.mymealplanner.common

import android.app.Application
import com.darrenfinch.mymealplanner.common.dependencyinjection.CompositionRoot

class Feasty : Application() {
    private lateinit var compositionRoot: CompositionRoot

    override fun onCreate() {
        super.onCreate()
        compositionRoot = CompositionRoot(this)
    }

    fun getCompositionRoot() = compositionRoot
}