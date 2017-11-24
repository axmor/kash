package com.axmor.kash.ui.navigation

import android.os.Bundle

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Call action() when get activate() - when we click on menu item in menu.
 * @see com.axmor.kash.ui.navigation.NavigationScene navigateInternal()
 */
open class NavigationItemAction(private val menuItId: Int, private val action: () -> Unit) : NavigationItem {

    override fun getMenuItemId(): Int {
        return menuItId
    }

    override fun getItemType(): Int {
        return NavigationItemTypes.INSTANT
    }

    override fun activate(bundle: Bundle?) {
        action()
    }
}