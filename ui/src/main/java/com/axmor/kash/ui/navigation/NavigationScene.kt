package com.axmor.kash.ui.navigation

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.util.SparseArrayCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.axmor.kash.ui.mvvm.KashActivity

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 *  Manages navigation in drawer layout.
 *  @see com.axmor.kash.sample.ui.main.MainMenuActivity buildNavigation()
 */

open class NavigationScene(hostActivity: KashActivity<*>, toolbar: Toolbar, val drawerLayout: DrawerLayout,
                           val drawerNavigation: NavigationView,
                           openDrawerContentDescRes: Int,
                           closeDrawerContentDescRes: Int,
                           val defaultItemId: Int) {

    private val SIS_KEY_ITEM_ID = NavigationScene::class.java.name + "ITEM_ID"
    private var drawerToggle: ActionBarDrawerToggle
    private val items = SparseArrayCompat<NavigationItem>()
    private var currentToggleItem: NavigationItem? = null

    init {
        drawerToggle = ActionBarDrawerToggle(hostActivity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerNavigation.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                return navigateInternal(menuItem, null)
            }
        })

    }

    fun onPostCreate(savedInstanceState: Bundle?) {
        drawerToggle.syncState()

        val itemId = savedInstanceState?.getInt(SIS_KEY_ITEM_ID, defaultItemId) ?: defaultItemId
        if (0 != itemId) {
            val menuItem = drawerNavigation.getMenu().findItem(itemId)
            if (null != menuItem) {
                navigateInternal(menuItem, null)
            }
        }
    }

    fun selectCurrent() {
        if (currentToggleItem != null) {
            drawerNavigation.getMenu().findItem(currentToggleItem!!.getMenuItemId()).setChecked(true)
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        if (null != currentToggleItem) {
            outState.putInt(SIS_KEY_ITEM_ID, currentToggleItem!!.getMenuItemId())
        }
    }

    fun addItem(navigationItem: NavigationItem) {
        items.append(navigationItem.getMenuItemId(), navigationItem)
    }

    fun navigate(menuItemId: Int, bundle: Bundle): Boolean {
        val menuItem = drawerNavigation.getMenu().findItem(menuItemId)
        return navigateInternal(menuItem, bundle)
    }


    protected fun navigateInternal(menuItem: MenuItem, bundle: Bundle?): Boolean {
        val navigationItem = items.get(menuItem.getItemId()) ?: return false

        val itemType = navigationItem.getItemType()
        when (itemType) {
            NavigationItemTypes.INSTANT -> {
                menuItem.setChecked(false)
                if (currentToggleItem != null) {
                    drawerNavigation.getMenu().findItem(currentToggleItem!!.getMenuItemId()).setChecked(true)
                }
                navigationItem.activate(bundle)
            }

            NavigationItemTypes.TOOGLE -> {
                if (currentToggleItem != navigationItem) {
                    currentToggleItem = navigationItem
                    menuItem.setChecked(true)
                    navigationItem.activate(bundle)
                }
            }
        }
        drawerLayout.closeDrawers()

        return true
    }
}