package com.axmor.kash.ui.error

/**
 * Created by akolodyazhnyy on 9/4/2017.
 */

/**
 * @see com.axmor.kash.ui.mvvm.KashActivity
 * @see com.axmor.kash.ui.mvvm.KashFragment
 */
interface ErrorListener {
    fun showError(error: String)
}