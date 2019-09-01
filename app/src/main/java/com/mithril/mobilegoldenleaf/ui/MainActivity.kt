package com.mithril.mobilegoldenleaf.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.interfaces.OnProductClikedListener
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.ui.category.fragments.CategoryListFragment
import com.mithril.mobilegoldenleaf.ui.dashboard.Dashboard
import com.mithril.mobilegoldenleaf.ui.product.fragments.ProductDetailsFragment
import com.mithril.mobilegoldenleaf.ui.product.fragments.ProductListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), OnProductClikedListener {

    private val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.app_name, R.string.app_name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectMenuOption(menuItem)
            true
        }

        if (savedInstanceState == null) {
            selectMenuOption(navigationView.menu.findItem(R.id.action_dashboard))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onProductClick(product: Product) {
        ProductDetailsFragment
    }

    private fun selectMenuOption(menuItem: MenuItem) {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        val fragment: Fragment = when (menuItem.itemId) {
            R.id.action_dashboard -> Dashboard.newInstance()
            R.id.action_product -> ProductListFragment.newInstance()
            R.id.action_category -> CategoryListFragment.newInstance()
            else -> {
                return
            }
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content, fragment)
        ft.commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }


}