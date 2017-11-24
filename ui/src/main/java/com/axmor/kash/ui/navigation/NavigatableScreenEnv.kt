package com.axmor.kash.ui.navigation

import android.content.Context
import android.os.Bundle
import com.axmor.kash.ui.mvvm.KashFragment

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Describing environment for navigation.
 * @see com.axmor.kash.sample.ui.main.MainMenuActivity
 */
interface NavigatableScreenEnv {

    fun getContext(): Context

    /**
     * Calling for set name of chosen screen to, for example, toolbar.
     */
    fun setTitle(charSequence: CharSequence?)

    /**
     * Calling for adding fragment to fragment container.
     */
    fun setRootFragment(fragment: KashFragment<*, *>, bundle: Bundle?)
}