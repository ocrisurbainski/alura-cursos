package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.MainActivityBinding
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navController by lazy {

        (supportFragmentManager.findFragmentById(R.id.main_activity_nav_host) as NavHostFragment)?.navController
    }
    private val viewModel: EstadoAppViewModel by viewModel()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        binding.mainActivityBottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            title = destination.label
        }

        viewModel.componentesVisuais.observe(this, Observer {
            it?.let { componentesVisuais ->

                when (componentesVisuais.hasAppBar) {
                    true -> supportActionBar?.show()
                    false -> supportActionBar?.hide()
                }

                when (componentesVisuais.hasBottomNavigation) {
                    true -> binding.mainActivityBottomNavigation.visibility = VISIBLE
                    false -> binding.mainActivityBottomNavigation.visibility = GONE
                }
            }
        })
    }

}