package com.mithril.mobilegoldenleaf.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.ui.category.CategoryListFragment
import com.mithril.mobilegoldenleaf.ui.category.OnProductsFromCategoryListener
import com.mithril.mobilegoldenleaf.ui.customer.CustomerFragment
import com.mithril.mobilegoldenleaf.ui.dashboard.Dashboard
import com.mithril.mobilegoldenleaf.ui.product.ProductListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), OnProductsFromCategoryListener,
        NavigationView.OnNavigationItemSelectedListener {

    private val clerk by lazy { intent.getParcelableExtra<Clerk>("clerk") }

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
        navigationView.setNavigationItemSelectedListener(this)

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

    override fun OnProductsFromCategoryClick(c: Category) {
        val fragment = ProductListFragment.newInstance(c.id)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    private fun displayFragment(id: Int) {
        val fragment: Fragment = when (id) {
            R.id.action_dashboard -> Dashboard.newInstance()
            R.id.action_category -> CategoryListFragment.newInstance()
            R.id.action_product -> ProductListFragment.newInstance()
            R.id.action_client -> CustomerFragment.newInstance()
            R.id.action_order -> Dashboard.newInstance()
            else -> {
                Dashboard.newInstance()
            }
        }

        supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        displayFragment(menuItem.itemId)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}