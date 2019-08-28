package com.mithril.mobilegoldenleaf.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), OnProductSavedListener {
    override fun onProductSaved(product: Product) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

    private fun selectMenuOption(menuItem: MenuItem) {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
    }


}