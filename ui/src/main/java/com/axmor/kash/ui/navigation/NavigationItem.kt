package com.axmor.kash.ui.navigation

import android.os.Bundle

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */


/**
 * Describe difference between elements in menu list (it can be a fragment or just an action)
 * @see com.axmor.kash.ui.navigation.NavigationItemAction - action
 * @see com.axmor.kash.ui.navigation.NavigationItemRootFragment - fragment
 */
interface NavigationItem {

    fun getMenuItemId(): Int
    fun getItemType(): Int

    fun activate(bundle: Bundle?)
}