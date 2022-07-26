package dev.robert.bagelly.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, true)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val bottomNavigationView = binding.bottomNavigation

        //bottomNavigationView.setupWithNavController(navController)
        setupWithNavController(bottomNavigationView, navController)

        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.searchFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.myAccountFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.editProfileFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.settingsFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.notificationsSettingsFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.notificationsFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                R.id.createShopFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                else -> {
                    supportActionBar?.show()
                    binding.bottomNavigation.visibility = android.view.View.VISIBLE
                }
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        super.onBackPressed()
    }
}