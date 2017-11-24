package com.axmor.kash.sample.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import com.axmor.kash.sample.R
import com.axmor.kash.sample.core.AppService
import com.axmor.kash.sample.ui.favorites.FavoritesListFragment
import com.axmor.kash.sample.ui.reddit_list.RedditListFragment
import com.axmor.kash.ui.mvvm.KashActivity
import com.axmor.kash.ui.mvvm.KashFragment
import com.axmor.kash.ui.navigation.NavigatableScreenEnv
import com.axmor.kash.ui.navigation.NavigationItemRootFragment
import com.axmor.kash.ui.navigation.NavigationScene
import kotlinx.android.synthetic.main.reddit_main_ac.*


/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class MainMenuActivity : KashActivity<MainMenuViewModel, AppService>(), NavigatableScreenEnv {

    private var navigationScene: NavigationScene? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reddit_main_ac)
        buildMenu()
        buildNavigation()
    }

    override fun getViewModelClass() = MainMenuViewModel::class.java

    fun buildMenu() {
        main_screen_drawer_navigation.inflateMenu(R.menu.reddit_main_menu_items)
        navigationScene = NavigationScene(
                this,
                reddit_main_toolbar,
                main_screen_drawer_layout
                , main_screen_drawer_navigation,
                R.string.password_toggle_content_description, R.string.password_toggle_content_description,
                R.id.main_drawer_navigation_item_list)
    }

    public override fun onResume() {
        super.onResume()
        navigationScene?.selectCurrent()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navigationScene?.onPostCreate(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        navigationScene?.onSaveInstanceState(outState!!)
    }


    override fun getContext() = this

    override fun setRootFragment(fragment: KashFragment<*, *>, bundle: Bundle?) {
        //check if already on screen
        var foundFragment = supportFragmentManager.findFragmentByTag(fragment.getFragmentTag())
        val isAlready = foundFragment != null
        foundFragment = if (isAlready) foundFragment else fragment
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.reddit_main_fr_container, foundFragment, fragment.getFragmentTag())

        //set bundle to arguments if need

        if (!isAlready) {
            transaction.addToBackStack((foundFragment as KashFragment<*, *>).getFragmentTag())
        }
        transaction.commitAllowingStateLoss()
    }

    private fun buildNavigation() {
        navigationScene?.addItem(NavigationItemRootFragment<RedditListFragment>(R.id.main_drawer_navigation_item_list,
                R.string.reddit_list_menu_item, this, { RedditListFragment() }))

        navigationScene?.addItem(NavigationItemRootFragment<FavoritesListFragment>(R.id.main_drawer_navigation_item_favorites,
                R.string.reddit_fav_menu_item, this, { FavoritesListFragment() }))
    }
}