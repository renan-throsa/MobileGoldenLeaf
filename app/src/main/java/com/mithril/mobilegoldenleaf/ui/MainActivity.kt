package com.mithril.mobilegoldenleaf.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.interfaces.OnProductClikedListener
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.category.fragments.CategoryListFragment
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnProductsFromCategoryListener
import com.mithril.mobilegoldenleaf.ui.dashboard.Dashboard
import com.mithril.mobilegoldenleaf.ui.product.fragments.ProductDetailsFragment
import com.mithril.mobilegoldenleaf.ui.product.fragments.ProductListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity(), OnProductClikedListener, OnProductsFromCategoryListener, NavigationView.OnNavigationItemSelectedListener {
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

    override fun onProductClick(product: Product) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            R.id.action_client -> Dashboard.newInstance()
            R.id.action_order -> Dashboard.newInstance()
            else -> {
                Dashboard.newInstance()
            }
        }

        supportFragmentManager
                .beginTransaction()
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

//    private fun selectMenuOption(menuItem: MenuItem) {
//        menuItem.isChecked = true
//        drawerLayout.closeDrawers()
//        val title = menuItem.title.toString()
//        if(supportFragmentManager.findFragmentByTag(title) == null){
//
//        }
//        val fragment: Fragment = when (menuItem.itemId) {
//            R.id.action_dashboard -> Dashboard.newInstance()
//            R.id.action_category -> CategoryListFragment.newInstance()
//            R.id.action_product -> ProductListFragment.newInstance()
//            else -> {
//                return
//            }
//        }
//
//
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.content, fragment,title)
//                .commit()
//
//        drawerLayout.closeDrawer(GravityCompat.START)
//    }


}