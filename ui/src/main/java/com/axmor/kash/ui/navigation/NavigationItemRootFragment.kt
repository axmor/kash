package com.axmor.kash.ui.navigation

import android.os.Bundle
import com.axmor.kash.ui.mvvm.KashFragment
import com.axmor.kash.ui.utils.loadCDataHtml

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * When that item clicked it using NavigatableScreenEnv to show fragment and set bundle
 * @see com.axmor.kash.ui.navigation.NavigationScene navigateInternal()
 */

open class NavigationItemRootFragment<T>(private val menuItemId: Int, private val titleResId: Int, private val screenEnv: NavigatableScreenEnv,
                                         private val fragmentFactory: () -> T) : NavigationItem where T : KashFragment<*, *> {

    override fun getMenuItemId(): Int {
        return menuItemId
    }

    override fun getItemType(): Int {
        return NavigationItemTypes.TOOGLE
    }

    override fun activate(bundle: Bundle?) {
        screenEnv.setTitle(loadCDataHtml(screenEnv.getContext(), titleResId))
        screenEnv.setRootFragment(fragmentFactory(), bundle)
    }
}