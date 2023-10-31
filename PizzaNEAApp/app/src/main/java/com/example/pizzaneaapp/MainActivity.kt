package com.example.pizzaneaapp

import android.os.Bundle //map strings to parcelables (container for reading and writing strings)
import android.view.MenuItem //access to previously added menu items
import android.widget.Toast //pop-up message for users
import androidx.appcompat.app.ActionBarDrawerToggle //has hamburger button contained in "action bar"
import androidx.appcompat.app.AppCompatActivity //base class for backported android features
import androidx.appcompat.widget.Toolbar //generalisation of action bar
import androidx.core.view.GravityCompat //newer functionality of gravity
import androidx.drawerlayout.widget.DrawerLayout //top-level container for drawer views
import com.google.android.material.navigation.NavigationView //standard app navigation


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //sets the starting view to be the "main" (home) page
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        //sets the actionbar to the necessary type to support hamburger menus
        setSupportActionBar(toolbar)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //allows actionbar to have a toggle to open the hamburger menu
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //if the app wasn't closed/suspended on a certain fragment, then it defaults to the homepage
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    //assigns each nav option to a fragment
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_deals -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DealsFragment()).commit()
            R.id.nav_account -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AccountFragment()).commit()
            R.id.nav_logout -> Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //animate the hamburger menu
    @Deprecated("Deprecated in Java") //i don't know why this line is here, the IDE just wanted it
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}