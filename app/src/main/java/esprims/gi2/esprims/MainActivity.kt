package esprims.gi2.esprims

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.ActivityMainBinding
import esprims.gi2.esprims.databinding.HeaderNavigationDrawerBinding

class MainActivity : AppCompatActivity() {
    var gradeUID: Int? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    var emlpoiEx: String? = null
    var emploi: String? = null
    var shouldFetch = true
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // return to APP style after splash screen
        setTheme(R.style.Theme_Esprims)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbae.setNavigationOnClickListener {
            val drawer = binding.drawerLayout
            drawer.open()

        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashbordFragment,
                R.id.loginFragment,
                R.id.emlpoiExamenFragment,
                R.id.noteFragment,
                R.id.payementFragment,
                R.id.actualityFragment,
                R.id.actualityFragment

            ),

            binding.drawerLayout
        )
        setSupportActionBar(binding.toolbae)
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.navView.menu.findItem(R.id.logOut).setOnMenuItemClickListener {

            Firebase.auth.signOut()
            finish()

            true
        }


    }


    //link toolbar with navigation view
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                ||
                super.onSupportNavigateUp()
    }


}